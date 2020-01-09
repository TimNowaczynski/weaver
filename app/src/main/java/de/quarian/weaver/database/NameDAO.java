package de.quarian.weaver.database;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import de.quarian.weaver.datamodel.NameSet;

@Dao
public interface NameDAO {

    @Query("SELECT * FROM nameset")
    List<NameSet> getNameSets();

    @Insert
    void addNameSet(final NameSet nameSet);

    @Update
    void updateNameSet(final NameSet nameSet);

    @Delete
    void deleteNameSet(final NameSet nameSet);
}
