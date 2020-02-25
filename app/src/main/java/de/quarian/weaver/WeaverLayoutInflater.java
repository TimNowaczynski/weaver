package de.quarian.weaver;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;

/**
 * Used to enhance testing capabilities
 */
public class WeaverLayoutInflater {

    public View inflate(@LayoutRes int layoutRes, final ViewGroup root) {
        return LayoutInflater.from(root.getContext()).inflate(layoutRes, root, false);
    }

}
