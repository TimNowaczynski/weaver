package de.quarian.weaver.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import de.quarian.weaver.database.AssetDAO;
import de.quarian.weaver.datamodel.Asset;
import de.quarian.weaver.datamodel.Event;

import static org.hamcrest.CoreMatchers.is;
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

    @Mock
    private AssetDAO assetDAOMock;

    @Mock
    private Asset assetMock;

    private Event dummyEvent;
    private AssetService assetService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        assetService = new AssetServiceImplementation(assetDAOMock);

        dummyEvent = new Event();
        dummyEvent.id = DUMMY_EVENT_ID;

        when(assetDAOMock.readAssetsForEvent(dummyEvent.id)).thenReturn(Collections.singletonList(assetMock));
        when(assetDAOMock.readAssetsForEvent(DUMMY_EVENT_ID)).thenReturn(Collections.singletonList(assetMock));
        when(assetDAOMock.readAllAssetsWithUnlimitedLifetime()).thenReturn(Collections.singletonList(assetMock));
        when(assetDAOMock.readAllAssetsWithLimitedLifetime()).thenReturn(Collections.singletonList(assetMock));
    }

    @Test
    public void testCreateAsset() {
        final Asset asset = mock(Asset.class);
        assetService.createAsset(asset);
        verify(assetDAOMock).createAsset(asset);
    }

    @Test
    public void testGetAssetsWithLimitedLifetime() {
        final List<Asset> assetsWithLimitedLifetime = assetService.getAssetsWithLimitedLifetime();
        verify(assetDAOMock).readAllAssetsWithLimitedLifetime();
        assertThat(assetsWithLimitedLifetime.get(0), is(assetMock));
    }

    @Test
    public void testGetAssetsWithUnlimitedLifetime() {
        final List<Asset> assetsWithLimitedLifetime = assetService.getAssetsWithUnlimitedLifetime();
        verify(assetDAOMock).readAllAssetsWithUnlimitedLifetime();
        assertThat(assetsWithLimitedLifetime.get(0), is(assetMock));
    }

    @Test
    public void testGetAssetForEvent() {
        final Asset assetForEvent = assetService.getAssetForEvent(dummyEvent);
        verify(assetDAOMock).readAssetsForEvent(dummyEvent.id);
        assertThat(assetForEvent, is(assetMock));
    }

    @Test
    public void testGetAssetForEventExtendsLifetime() {
        assetMock.endOfLifetimeTimestamp = 10L;
        final Asset assetForEvent = assetService.getAssetForEvent(dummyEvent);
        verify(assetDAOMock).updateAsset(assetForEvent);
    }

    @Test
    public void testGetAssetForEventDoesNotOverrideUnlimitedLifetime() {
        final Asset assetForEvent = assetService.getAssetForEvent(dummyEvent);
        verify(assetDAOMock, never()).updateAsset(assetForEvent);
    }

    @Test
    public void testMoveAssetToCloud() {
        assetMock.asset = new byte[10];
        assetService.moveAssetToCloud(assetMock);

        final ArgumentCaptor<Asset> assetArgumentCaptor = ArgumentCaptor.forClass(Asset.class);
        verify(assetDAOMock, times(1)).updateAsset(assetArgumentCaptor.capture());

        final Asset asset = assetArgumentCaptor.getValue();
        assertThat(asset.asset, nullValue());
        assertThat(asset.fallbackUrl, is("http://fallback.url"));
        assertThat(asset.endOfLifetimeTimestamp, is(AssetService.UNLIMITED_LIFETIME));
    }

    @Test
    public void testExtendLifetime() {
        assetService.extendLifetime(assetMock, 10000L);

        final ArgumentCaptor<Asset> argumentCaptor = ArgumentCaptor.forClass(Asset.class);
        verify(assetDAOMock).updateAsset(argumentCaptor.capture());
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
