package com.yawarSoft.Core.Utils;

import com.yawarSoft.Core.Entities.PersonEntity;
import com.yawarSoft.Core.Entities.UserEntity;
import org.mapstruct.Context;
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


    @Named("getFullNamePerson")
    public static String getFullName(PersonEntity person, @Context AESGCMEncryptionUtil aesUtil) {
        if (person == null) return null;

        try {
            String firstName = person.getFirstName() != null
                    //aesUtil.decrypt(new String(person.getFirstName(), StandardCharsets.UTF_8)
                    ? aesUtil.decrypt(new String(person.getFirstName()))
                    : "";
            String lastName = person.getLastName() != null
                    ? aesUtil.decrypt(new String(person.getLastName()))
                    : "";
            String secondLastName = person.getSecondLastName() != null
                    ? aesUtil.decrypt(new String(person.getSecondLastName()))
                    : "";

            return String.join(" ", firstName, lastName, secondLastName).trim();
        } catch (Exception e) {
            System.out.println("ERROR getFullNamePerson DECRYPT");
            return null;
        }
    }

}
