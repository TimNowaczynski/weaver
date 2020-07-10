package de.quarian.weaver.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import de.quarian.weaver.datamodel.ddo.AssetDisplayObject;
import de.quarian.weaver.database.AssetDAO;
import de.quarian.weaver.database.DAOProvider;
import de.quarian.weaver.datamodel.Asset;
import de.quarian.weaver.datamodel.Event;
import de.quarian.weaver.datamodel.converter.AssetConverter;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AssetServiceImplementationUnitTest {

    private static final long DUMMY_EVENT_ID = 123L;
    private static final long DUMMY_ASSET_ID = 456L;

    @Mock
    private AssetDAO assetDAOMock;

    @Mock
    private DAOProvider daoProvider;

    @Mock
    private AssetConverter assetConverter;

    @Mock
    private List<AssetDisplayObject> assetDisplayObjectList;

    @Mock
    private AssetDisplayObject assetDisplayObject;

    private Asset dummyAsset;
    private Event dummyEvent;
    private AssetService assetService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(daoProvider.assetDAO()).thenReturn(assetDAOMock);
        assetService = new AssetServiceImplementation(daoProvider, assetConverter);

        dummyEvent = new Event();
        dummyEvent.id = DUMMY_EVENT_ID;

        dummyAsset = new Asset();
        dummyAsset.id = DUMMY_ASSET_ID;

        final List<Asset> results = Collections.singletonList(dummyAsset);
        when(assetDAOMock.readAssetsForEvent(dummyEvent.id)).thenReturn(results);
        when(assetDAOMock.readAssetsForEvent(DUMMY_EVENT_ID)).thenReturn(results);
        when(assetDAOMock.readAllAssetsWithUnlimitedLifetime()).thenReturn(results);
        when(assetDAOMock.readAllAssetsWithLimitedLifetime()).thenReturn(results);
        when(assetDAOMock.readAssetForId(DUMMY_ASSET_ID)).thenReturn(dummyAsset);

        when(assetConverter.convert(results)).thenReturn(assetDisplayObjectList);
        when(assetConverter.convert(Collections.singletonList(dummyAsset))).thenReturn(Collections.singletonList(assetDisplayObject));
        when(assetConverter.convert(dummyAsset)).thenReturn(assetDisplayObject);
    }

    @Test
    public void testCreateAsset() {
        final Asset asset = mock(Asset.class);
        assetService.createAsset(asset);
        verify(assetDAOMock).createAsset(asset);
    }

    @Test
    public void testGetAssetsWithLimitedLifetime() {
        final List<AssetDisplayObject> assetsWithLimitedLifetime = assetService.getAssetsWithLimitedLifetime();
        verify(assetDAOMock).readAllAssetsWithLimitedLifetime();
        assertThat(assetsWithLimitedLifetime.get(0), is(assetDisplayObject));
    }

    @Test
    public void testGetAssetsWithUnlimitedLifetime() {
        final List<AssetDisplayObject> assetsWithLimitedLifetime = assetService.getAssetsWithUnlimitedLifetime();
        verify(assetDAOMock).readAllAssetsWithUnlimitedLifetime();
        verify(assetConverter).convert(Collections.singletonList(dummyAsset));
        assertThat(assetsWithLimitedLifetime.get(0), is(assetDisplayObject));
    }

    @Test
    public void testGetAssetForEvent() {
        final AssetDisplayObject assetForEvent = assetService.getAssetForEvent(dummyEvent.id);
        verify(assetDAOMock).readAssetsForEvent(dummyEvent.id);
        verify(assetConverter).convert(dummyAsset);
        assertThat(assetForEvent, notNullValue());
    }

    @Test
    public void testGetAssetForEventExtendsLifetime() {
        dummyAsset.endOfLifetimeTimestamp = 10L;
        assetService.getAssetForEvent(DUMMY_EVENT_ID);
        verify(assetConverter).convert(dummyAsset);
        verify(assetDAOMock).updateAsset(dummyAsset);
    }

    @Test
    public void testGetAssetForEventDoesNotOverrideUnlimitedLifetime() {
        assetService.getAssetForEvent(DUMMY_EVENT_ID);

        verify(assetDAOMock).readAssetsForEvent(DUMMY_EVENT_ID);
        verify(assetDAOMock, never()).updateAsset(dummyAsset);
    }

    @Test
    public void testMoveAssetToCloud() {
        dummyAsset.asset = new Byte[10];
        assetService.moveAssetToCloud(DUMMY_ASSET_ID);

        final ArgumentCaptor<Asset> assetArgumentCaptor = ArgumentCaptor.forClass(Asset.class);
        verify(assetDAOMock, times(1)).updateAsset(assetArgumentCaptor.capture());

        final Asset asset = assetArgumentCaptor.getValue();
        assertThat(asset.asset, nullValue());
        assertThat(asset.fallbackUrl, is("http://fallback.url"));
        assertThat(asset.endOfLifetimeTimestamp, is(AssetService.UNLIMITED_LIFETIME));
    }

    @Test
    public void testExtendLifetime() {
        assetService.extendLifetime(DUMMY_ASSET_ID, 10000L);

        final ArgumentCaptor<Asset> argumentCaptor = ArgumentCaptor.forClass(Asset.class);
        verify(assetDAOMock).updateAsset(argumentCaptor.capture());
        final Asset capturedAsset = argumentCaptor.getValue();
        assertThat(capturedAsset.id, is(DUMMY_ASSET_ID));
    }

    @Test
    public void testCleanUpExpiredAssets() {
        assetService.cleanUpExpiredAssets();
        verify(assetDAOMock).deleteAllExpiredAssets(anyLong());
    }

    @Test
    public void testReadNumberOfExpiredAssets() {
        when(assetDAOMock.readNumberOfExpiredAssets(anyLong())).thenReturn(7);
        final int numberOfExpiredAssets = assetService.getNumberOfExpiredAssets();
        assertThat(numberOfExpiredAssets, is(7));
    }
}
