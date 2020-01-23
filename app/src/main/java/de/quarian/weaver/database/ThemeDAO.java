package de.quarian.weaver.database;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import de.quarian.weaver.datamodel.Theme;

@Dao
public interface ThemeDAO {

    @Insert
    long createTheme(final Theme theme);

    @Query("SELECT * FROM Theme")
    List<Theme> readThemes();

    @Query("SELECT * FROM Theme WHERE theme_id IS :id")
    Theme readThemeByID(final long id);

    @Update
    void updateTheme(final Theme theme);
}
