package de.quarian.weaver;

import android.os.Bundle;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import de.quarian.weaver.di.DependencyInjectionListener;
import de.quarian.weaver.di.DependencyInjector;
import de.quarian.weaver.util.Logger;
import de.quarian.weaver.util.LoggingProvider;

public abstract class WeaverActivity extends AppCompatActivity implements DependencyInjectionListener {

    public static final String EXTRA_CAMPAIGN_ID = "extra.campaignId";
    private static final long INVALID_CAMPAIGN_ID = -2;

    public static class WeaverActivityDependencies {

        @Inject
        public LoggingProvider loggingProvider;

        @Inject
        @Nullable
        public ActivityPreconditionErrorHandler errorHandler;

    }

    public final WeaverActivityDependencies weaverActivityDependencies = new WeaverActivityDependencies();

    public long campaignId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DependencyInjector.get().injectDependencies(this);
    }

    public Logger getLogger(final Object object) {
        return weaverActivityDependencies.loggingProvider.getLogger(object);
    }

    protected boolean requireCampaignId() {
        if (weaverActivityDependencies.errorHandler != null) {
            campaignId = getIntent().getLongExtra(EXTRA_CAMPAIGN_ID, INVALID_CAMPAIGN_ID);
            return weaverActivityDependencies.errorHandler.requireOrFinish(() -> campaignId != INVALID_CAMPAIGN_ID, R.string.activity_player_character_list_invalid_id_error_title, R.string.activity_player_character_list_invalid_id_error_text);
        } else {
            finish(); // Should basically never happen
            return false;
        }
    }
}
