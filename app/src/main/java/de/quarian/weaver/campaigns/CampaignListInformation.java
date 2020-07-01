package de.quarian.weaver.campaigns;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CampaignListInformation {

    public enum Priority {
        NORMAL, URGENT, HIGHEST
    }

    @NonNull
    private final Priority priority;

    @NonNull
    private final String message;

    @Nullable
    private Runnable action = null;

    public CampaignListInformation(@NonNull final Priority priority, @NonNull final String message) {
        this.priority = priority;
        this.message = message;
    }

    public void setAction(@Nullable Runnable action) {
        this.action = action;
    }

    @NonNull
    public Priority getPriority() {
        return priority;
    }

    @NonNull
    public String getMessage() {
        return message;
    }

    //TODO: threading? maybe more in the runnables themselves if needed
    public void performAction() {
        if (action == null) {
            return;
        }

        action.run();
    }
}
