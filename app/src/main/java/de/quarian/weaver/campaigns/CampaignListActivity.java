package de.quarian.weaver.campaigns;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.quarian.weaver.NavigationController;
import de.quarian.weaver.R;

public class CampaignListActivity extends AppCompatActivity {

    private List<CampaignDisplayObject> campaignDisplayObjects = new ArrayList<>();

    public CampaignListActivity() {
        //TODO: replace dummy code
        campaignDisplayObjects.add(new CampaignDisplayObject(1, "Campaign 1"));
        campaignDisplayObjects.add(new CampaignDisplayObject(2, "Campaign 2"));
        campaignDisplayObjects.add(new CampaignDisplayObject(3, "Campaign 3"));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campaign_list);
        setTitle(R.string.activity_title_campaign_screen);
        setUpFloatingActionButton();

        final RecyclerView campaignList = findViewById(R.id.campaign_list);
        final Context baseContext = getBaseContext();
        campaignList.setLayoutManager(new LinearLayoutManager(baseContext));
        campaignList.setHasFixedSize(true);

        final CampaignListAdapter campaignListAdapter = new CampaignListAdapter(this);
        campaignList.setAdapter(campaignListAdapter);
        campaignListAdapter.setCampaignDisplayObjects(campaignDisplayObjects);
    }

    private void setUpFloatingActionButton() {
        final View floatingActionButton = findViewById(R.id.campaign_list_add_campaign);
        floatingActionButton.setOnClickListener((view) -> NavigationController.getInstance().addCampaign(this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CampaignActivity.REQUEST_CODE_MODIFY_CAMPAIGNS && resultCode == Activity.RESULT_OK) {
            refreshList();
        }
    }

    private void refreshList() {
        final RecyclerView campaignList = findViewById(R.id.campaign_list);
        final RecyclerView.Adapter adapter = campaignList.getAdapter();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }
}
