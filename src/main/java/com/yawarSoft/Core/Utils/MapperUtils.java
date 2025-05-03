package com.yawarSoft.Core.Utils;

import com.yawarSoft.Core.Entities.PersonEntity;
import com.yawarSoft.Core.Entities.UserEntity;
import lombok.SneakyThrows;
import org.mapstruct.Context;
import org.mapstruct.Named;

import java.util.Arrays;
import java.util.Base64;
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


    @Named("getFullNamePerson")
    public static String getFullName(PersonEntity person, @Context AESGCMEncryptionUtil aesUtil) {
        if (person == null) return null;

        try {
            String firstName = person.getFirstName() != null
                    ? aesUtil.decrypt(Base64.getEncoder().encodeToString(person.getFirstName()))
                    : "";
            String lastName = person.getLastName() != null
                    ? aesUtil.decrypt(Base64.getEncoder().encodeToString(person.getLastName()))
                    : "";
            String secondLastName = person.getSecondLastName() != null
                    ? aesUtil.decrypt(Base64.getEncoder().encodeToString(person.getSecondLastName()))
                    : "";

            return String.join(" ", firstName, lastName, secondLastName).trim();
        } catch (Exception e) {
            // Log y manejo de errores según tu política
            return null;
        }
    }

}
