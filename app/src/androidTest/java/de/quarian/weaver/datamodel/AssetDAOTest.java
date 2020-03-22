package de.quarian.weaver.datamodel;

import android.content.Context;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import de.quarian.weaver.database.AssetDAO;
import de.quarian.weaver.database.CharacterDAO;
import de.quarian.weaver.database.WeaverDB;

import static de.quarian.weaver.datamodel.DatabaseTestConstants.ASSET_DESCRIPTION;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.ASSET_FALLBACK_URL;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.ASSET_IMAGE;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.ASSET_IMAGE_TYPE;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.ASSET_NAME;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.MOONLIGHT_ALIAS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThanOrEqualTo;

public class AssetDAOTest {

    private WeaverDB weaverDB;

    @Before
    public void setUpDatabase() {
        final Context applicationContext = ApplicationProvider.getApplicationContext();
        this.weaverDB = Room.inMemoryDatabaseBuilder(applicationContext, WeaverDB.class).build();
        DatabaseTestUtils.setUpRoleplayingSystems(this.weaverDB);
        DatabaseTestUtils.setUpThemes(this.weaverDB);
        DatabaseTestUtils.setUpCampaigns(this.weaverDB);
        DatabaseTestUtils.setUpCharacters(this.weaverDB);
        //DatabaseTestUtils.setUpTags(this.weaverDB);
        DatabaseTestUtils.setUpEvents(this.weaverDB);
        DatabaseTestUtils.setUpAssets(this.weaverDB);
    }

    @After
    public void closeDatabase() {
        this.weaverDB.close();
    }

    @Test
    public void testCreateAndReadAssets() {
        final CharacterDAO characterDAO = weaverDB.characterDAO();
        final CharacterHeader moonlight = characterDAO.readCharacterHeaderByAlias(MOONLIGHT_ALIAS);
        final List<Event> events = characterDAO.readEventsForCharacterHeaderId(moonlight.id);
        assertThat(events, hasSize(1));

        // Crate is done by DatabaseTestUtils.setUpAssets(this.weaverDB)

        final AssetDAO assetDAO = weaverDB.assetDAO();
        final long eventId = events.get(0).id;
        final List<Asset> assets = assetDAO.readAssetsForEvent(eventId);
        assertThat(assets, hasSize(1));

        final Asset asset = assets.get(0);
        assertThat(asset.contentLocallyPresent, is(true));
        assertThat(asset.eventId, is(eventId));
        assertThat(asset.assetName, is(ASSET_NAME));
        assertThat(asset.assetDescription, is(ASSET_DESCRIPTION));
        assertThat(asset.assetType, is(ASSET_IMAGE_TYPE));
        assertThat(asset.asset, is(ASSET_IMAGE));
        assertThat(asset.fallbackUrl, is(ASSET_FALLBACK_URL));

        final long now = System.currentTimeMillis();
        final long oneDay = 1000L * 60L * 60L * 24L;
        final long tolerance = 1000L * 2L;
        assertThat(asset.endOfLifetimeTimestamp, greaterThan(now));
        assertThat(asset.endOfLifetimeTimestamp, lessThanOrEqualTo(now + oneDay + tolerance));
    }

    @Test
    public void testDeleteAsset() {
        final CharacterDAO characterDAO = weaverDB.characterDAO();
        final CharacterHeader moonlight = characterDAO.readCharacterHeaderByAlias(MOONLIGHT_ALIAS);
        final List<Event> events = characterDAO.readEventsForCharacterHeaderId(moonlight.id);

        final AssetDAO assetDAO = weaverDB.assetDAO();
        final long eventId = events.get(0).id;
        List<Asset> assets = assetDAO.readAssetsForEvent(eventId);
        final Asset asset = assets.get(0);

        assertThat(assets, hasSize(1));
        assetDAO.deleteAsset(asset);

        assets = assetDAO.readAssetsForEvent(eventId);
        assertThat(assets, hasSize(0));
    }

    @Test
    public void testDeleteAssociatedEventDeletesAsset() {
        final CharacterDAO characterDAO = weaverDB.characterDAO();
        final CharacterHeader moonlight = characterDAO.readCharacterHeaderByAlias(MOONLIGHT_ALIAS);
        final List<Event> events = characterDAO.readEventsForCharacterHeaderId(moonlight.id);

        final AssetDAO assetDAO = weaverDB.assetDAO();
        final Event event = events.get(0);
        List<Asset> assets = assetDAO.readAssetsForEvent(event.id);
        assertThat(assets.size(), is(1));

        characterDAO.deleteEvent(event);
        assets = assetDAO.readAssetsForEvent(event.id);
        assertThat(assets.size(), is(0));
    }

    @Test
    public void testReadAssetsWithInfiniteLifetime() {
        final CharacterDAO characterDAO = weaverDB.characterDAO();
        final CharacterHeader moonlight = characterDAO.readCharacterHeaderByAlias(MOONLIGHT_ALIAS);
        final List<Event> events = characterDAO.readEventsForCharacterHeaderId(moonlight.id);
        assertThat(events, hasSize(1));

        // Crate is done by DatabaseTestUtils.setUpAssets(this.weaverDB)

        final AssetDAO assetDAO = weaverDB.assetDAO();
        final long eventId = events.get(0).id;
        List<Asset> assets = assetDAO.readAssetsForEvent(eventId);
        assertThat(assets, hasSize(1));

        final Asset asset = new Asset();
        asset.eventId = eventId;
        asset.endOfLifetimeTimestamp = 0;
        asset.assetName = "Dummy";
        asset.assetType = "*/blob";
        assetDAO.createAsset(asset);

        assets = assetDAO.readAssetsForEvent(eventId);
        assertThat(assets, hasSize(2));

        assets = assetDAO.readAllAssetsWithIndefiniteLifetime();
        assertThat(assets, hasSize(1));
    }

    @Test
    public void testReadAllExpireAssets() {
        final CharacterDAO characterDAO = weaverDB.characterDAO();
        final CharacterHeader moonlight = characterDAO.readCharacterHeaderByAlias(MOONLIGHT_ALIAS);
        final List<Event> events = characterDAO.readEventsForCharacterHeaderId(moonlight.id);
        assertThat(events, hasSize(1));

        // Crate is done by DatabaseTestUtils.setUpAssets(this.weaverDB)

        final AssetDAO assetDAO = weaverDB.assetDAO();
        final long eventId = events.get(0).id;
        List<Asset> assets = assetDAO.readAssetsForEvent(eventId);
        assertThat(assets, hasSize(1));

        final Asset asset = assets.get(0);
        final long timestamp = System.currentTimeMillis();
        asset.endOfLifetimeTimestamp = timestamp - 200;
        assetDAO.updateAsset(asset);

        assets = assetDAO.readAllAssetsWithLimitedLifetime();
        assertThat(assets, hasSize(1));

        assertThat(asset.endOfLifetimeTimestamp, is(greaterThan(0L)));
    }

    @Test
    public void testDeleteExpiredAssets() {
        final CharacterDAO characterDAO = weaverDB.characterDAO();
        final CharacterHeader moonlight = characterDAO.readCharacterHeaderByAlias(MOONLIGHT_ALIAS);
        final List<Event> events = characterDAO.readEventsForCharacterHeaderId(moonlight.id);
        assertThat(events, hasSize(1));

        // Crate is done by DatabaseTestUtils.setUpAssets(this.weaverDB)

        final AssetDAO assetDAO = weaverDB.assetDAO();
        final long eventId = events.get(0).id;
        List<Asset> assets = assetDAO.readAssetsForEvent(eventId);
        assertThat(assets, hasSize(1));

        final Asset asset = assets.get(0);
        asset.endOfLifetimeTimestamp = 1L;
        assetDAO.updateAsset(asset);

        assetDAO.deleteAllExpiredAssets(2L);
        assets = assetDAO.readAssetsForEvent(eventId);
        assertThat(assets, hasSize(0));
    }
}
