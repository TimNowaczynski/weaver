package de.quarian.weaver.service;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import de.quarian.weaver.database.RoleplayingSystemDAO;
import de.quarian.weaver.database.WeaverDB;
import de.quarian.weaver.datamodel.RoleplayingSystem;

public class RoleplayingSystemServiceImplementation implements RoleplayingSystemService {

    private final RoleplayingSystemDAO roleplayingSystemDAO;

    public RoleplayingSystemServiceImplementation(@NonNull final WeaverDB weaverDB) {
        roleplayingSystemDAO = weaverDB.roleplayingSystemDAO();
    }

    @Override
    public void createRoleplayingSystem(@NonNull RoleplayingSystem roleplayingSystem) {
        roleplayingSystemDAO.createRoleplayingSystem(roleplayingSystem);
    }

    @NonNull
    @Override
    public List<RoleplayingSystem> readRoleplayingSystems() {
        return roleplayingSystemDAO.readRoleplayingSystems();
    }

    @Nullable
    @Override
    public RoleplayingSystem readRoleplayingSystemById(long roleplayingSystemId) {
        return roleplayingSystemDAO.readRoleplayingSystemsById(roleplayingSystemId);
    }

    @Override
    public void updateRoleplayingSystem(@NonNull RoleplayingSystem roleplayingSystem) {
        roleplayingSystemDAO.updateRoleplayingSystem(roleplayingSystem);
    }

    @Override
    public void deleteRoleplayingSystem(@NonNull RoleplayingSystem roleplayingSystem) {
        roleplayingSystemDAO.deleteRoleplayingSystem(roleplayingSystem);
    }
}
