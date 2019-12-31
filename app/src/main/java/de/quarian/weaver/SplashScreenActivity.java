package de.quarian.weaver;

import androidx.appcompat.app.AppCompatActivity;
import de.quarian.weaver.campaigns.CampaignListActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
    }

    @Override
    protected void onResume() {
        super.onResume();

        final Context applicationContext = getApplicationContext();
        final Intent intent = new Intent(applicationContext, CampaignListActivity.class);

        // Start with a delay to show the splash screen. Later on we could do startup
        // preparations if needed as well.
        final Handler handler = new Handler();
        handler.postDelayed(() -> startActivity(intent), 1000);
    }
}
