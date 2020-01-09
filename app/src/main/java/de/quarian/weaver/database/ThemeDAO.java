package de.quarian.weaver.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import de.quarian.weaver.datamodel.Theme;

@Dao
public interface ThemeDAO {

    @Query("SELECT * FROM Theme WHERE theme_id IS :id")
    Theme getTheme(final long id);

    @Insert
    long insertTheme(final Theme theme);

    @Update
    void updateTheme(final Theme theme);
}
