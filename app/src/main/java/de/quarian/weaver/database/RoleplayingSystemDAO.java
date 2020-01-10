package de.quarian.weaver.database;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import de.quarian.weaver.datamodel.RoleplayingSystem;

@Dao
public interface RoleplayingSystemDAO {

    @Insert
    long createRoleplayingSystem(final RoleplayingSystem roleplayingSystem);

    @Query("SELECT * FROM RoleplayingSystem")
    List<RoleplayingSystem> readRoleplayingSystems();

    @Update
    void updateRoleplayingSystem(final RoleplayingSystem roleplayingSystem);

    @Delete
    void deleteRoleplayingSystem(final RoleplayingSystem roleplayingSystem);
}
