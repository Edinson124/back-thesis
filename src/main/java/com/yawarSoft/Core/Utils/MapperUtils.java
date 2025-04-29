package com.yawarSoft.Core.Utils;

import com.yawarSoft.Core.Entities.UserEntity;
import org.mapstruct.Named;

import java.util.Optional;

public class MapperUtils {

    @Named("getFullName")
    public static String getFullName(UserEntity coordinator) {
        if (coordinator == null) return null;
        return String.join(" ",
                Optional.ofNullable(coordinator.getFirstName()).orElse(""),
                Optional.ofNullable(coordinator.getLastName()).orElse(""),
                Optional.ofNullable(coordinator.getSecondLastName()).orElse("")
        ).trim();
    }
}
