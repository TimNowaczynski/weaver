package de.quarian.weaver.campaigns;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ClickableSpan;
import android.view.View;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;

/**
    TODO: Find a suitable service provider to provide dynamic information
    TODO: Think about having a welcome/tutorial message here
 */
public class CampaignListInformationHandler {

    private final DateFormat dateFormat;
    private Map<CampaignListInformation.Priority, List<CampaignListInformation>> information = new HashMap<>(3);

    public CampaignListInformationHandler() {
        dateFormat = SimpleDateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT);
    }

    @VisibleForTesting
    public CampaignListInformationHandler(final Locale locale) {
        dateFormat = SimpleDateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, locale);
    }

    // TODO: later on, query instead of adding manually
    // TODO: also, check individual information like attachments which are scheduled to delete
    public void addInformation(@NonNull final CampaignListInformation campaignListInformation) {
        final CampaignListInformation.Priority priority = campaignListInformation.getPriority();
        List<CampaignListInformation> information = this.information.get(priority);

        if (information == null) {
            information = new ArrayList<>(1);
            this.information.put(priority, information);
        }

        information.add(campaignListInformation);
    }

    @NonNull
    public String getProcessedInformation() {
        if (information.size() == 0) {
            return getDefaultString();
        }

        String information = concatenateInformation(CampaignListInformation.Priority.HIGHEST);
        if (information.length() > 0) {
            return information;
        }

        information = concatenateInformation(CampaignListInformation.Priority.URGENT);
        if (information.length() > 0) {
            return information;
        }

        return concatenateInformation(CampaignListInformation.Priority.NORMAL);
    }

    private String getDefaultString() {
        // TODO: add something like a bold suffix with " - No new messages"
        return dateFormat.format(new Date());
    }

    private String concatenateInformation(@NonNull final CampaignListInformation.Priority priority) {
        final List<CampaignListInformation> informationList = this.information.get(priority);
        if (informationList == null) {
            return "";
        }

        final SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder("");
        for (final CampaignListInformation campaignListInformation : informationList) {
            final String message = campaignListInformation.getMessage();
            final int startPosition = spannableStringBuilder.length();
            final int endPosition = startPosition + message.length();
            spannableStringBuilder.append(message).append(" ");

            final ClickableSpan clickableSpan = new ClickableSpan() {
                @Override
                public void onClick(@NonNull View widget) {
                    campaignListInformation.performAction();
                }
            };

            spannableStringBuilder.setSpan(clickableSpan, startPosition, endPosition, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        return spannableStringBuilder.toString();
    }
}
