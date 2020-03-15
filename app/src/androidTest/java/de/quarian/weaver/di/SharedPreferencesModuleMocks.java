package de.quarian.weaver.di;

import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyFloat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SharedPreferencesModuleMocks {

    public SharedPreferences campaignListOrderPreferences;
    public SharedPreferences.Editor campaignListOrderPreferencesEditor;

    public static SharedPreferencesModuleMocks create() {
        return new SharedPreferencesModuleMocks();
    }

    private SharedPreferencesModuleMocks() {
        campaignListOrderPreferences = mock(SharedPreferences.class);
        campaignListOrderPreferencesEditor = mock(SharedPreferences.Editor.class);
        when(campaignListOrderPreferences.edit()).thenReturn(campaignListOrderPreferencesEditor);
        mockSharedPreferencesEditor(campaignListOrderPreferencesEditor);
    }

    private void mockSharedPreferencesEditor(@NonNull SharedPreferences.Editor sharedPreferencesEditorMock) {
        when(sharedPreferencesEditorMock.putString(anyString(), anyString())).thenReturn(sharedPreferencesEditorMock);
        when(sharedPreferencesEditorMock.putBoolean(anyString(), anyBoolean())).thenReturn(sharedPreferencesEditorMock);
        when(sharedPreferencesEditorMock.putFloat(anyString(), anyFloat())).thenReturn(sharedPreferencesEditorMock);
        when(sharedPreferencesEditorMock.putInt(anyString(), anyInt())).thenReturn(sharedPreferencesEditorMock);
        when(sharedPreferencesEditorMock.putLong(anyString(), anyLong())).thenReturn(sharedPreferencesEditorMock);
        // Mock put Set<String>?
    }
}
