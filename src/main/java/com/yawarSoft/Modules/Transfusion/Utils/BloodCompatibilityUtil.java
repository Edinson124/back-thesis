package com.yawarSoft.Modules.Transfusion.Utils;

import java.util.List;
import java.util.Map;

public class BloodCompatibilityUtil {

    private static final Map<String, List<String>> compatibilityMap = Map.of(
            "O-", List.of("O-"),
            "O+", List.of("O-", "O+"),
            "A-", List.of("O-", "A-"),
            "A+", List.of("O-", "O+", "A-", "A+"),
            "B-", List.of("O-", "B-"),
            "B+", List.of("O-", "O+", "B-", "B+"),
            "AB-", List.of("O-", "A-", "B-", "AB-"),
            "AB+", List.of("O-", "O+", "A-", "A+", "B-", "B+", "AB-", "AB+")
    );

    public static List<String> getCompatibleDonors(String recipientBloodType) {
        return compatibilityMap.getOrDefault(recipientBloodType, List.of());
    }
}