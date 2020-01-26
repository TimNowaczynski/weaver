package de.quarian.weaver.service;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import de.quarian.weaver.datamodel.RoleplayingSystem;

public interface RoleplayingSystemService {

    void createRoleplayingSystem(@NonNull final RoleplayingSystem roleplayingSystem);

    @NonNull
    List<RoleplayingSystem> readRoleplayingSystems();

    @Nullable
    RoleplayingSystem readRoleplayingSystemById(final long roleplayingSystemId);

    void updateRoleplayingSystem(@NonNull final RoleplayingSystem roleplayingSystem);

    void deleteRoleplayingSystem(@NonNull final RoleplayingSystem roleplayingSystem);
}
