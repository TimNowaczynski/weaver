package de.quarian.weaver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
    }

    @Override
    protected void onResume() {
        super.onResume();
        
        // Start with a delay to show the splash screen. Later on we could do startup
        // preparations if needed as well.
        final Handler handler = new Handler();
        handler.postDelayed(() -> NavigationController.getInstance().openCampaignList(this), 1000);
    }
}
