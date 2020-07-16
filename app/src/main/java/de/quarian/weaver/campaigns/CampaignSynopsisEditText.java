package de.quarian.weaver.campaigns;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;

import java.lang.ref.WeakReference;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;

public class CampaignSynopsisEditText extends AppCompatEditText {

    public interface Callbacks {
        void onFocusChanged(boolean focused);
    }

    private static final long FOCUS_CHANGED_COOL_DOWN_TIME = 10L;
    private static final long NOTIFICATION_COOL_DOWN_TIME = 250L;

    private WeakReference<Callbacks> callbacks;
    private long lastFocusChange = 0L;
    private long lastTimeNotified = 0L;

    public CampaignSynopsisEditText(Context context) {
        super(context);
    }

    public CampaignSynopsisEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CampaignSynopsisEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setCallbacks(@Nullable final Callbacks callbacks) {
        this.callbacks = new WeakReference<>(callbacks);
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        final long nextFocusChange = lastFocusChange + FOCUS_CHANGED_COOL_DOWN_TIME;
        final long now = System.currentTimeMillis();
        if (now > nextFocusChange && !focused) {
            requestFocus();
        } else {
            lastFocusChange = now;
            notifyAboutFocusChange(focused);
        }
    }

    private void notifyAboutFocusChange(final boolean focused) {
        final long now = System.currentTimeMillis();
        if (lastTimeNotified + NOTIFICATION_COOL_DOWN_TIME > now) {
            return;
        }

        final Callbacks callbacks = this.callbacks.get();
        if (callbacks != null) {
            lastTimeNotified = now;
            callbacks.onFocusChanged(focused);
        }
    }
}
