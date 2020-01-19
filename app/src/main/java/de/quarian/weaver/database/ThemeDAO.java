package de.quarian.weaver.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import de.quarian.weaver.datamodel.Theme;

@Dao
public interface ThemeDAO {

    @Insert
    long createTheme(final Theme theme);

    @Query("SELECT * FROM Theme WHERE theme_id IS :id")
    LiveData<Theme> readTheme(final long id);

    @Update
    void updateTheme(final Theme theme);
}
