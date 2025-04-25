package com.yawarSoft.Core.Utils;

import java.security.SecureRandom;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PasswordGenerator {
    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL = "#@*=+";
    private static final String ALL_CHARS = LOWER + UPPER + DIGITS + SPECIAL;
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generateRandomPassword() {
        int length = 12 + RANDOM.nextInt(9); // Genera longitud entre 12 y 20
        StringBuilder password = new StringBuilder();

        // Asegurar al menos un carácter de cada tipo
        password.append(LOWER.charAt(RANDOM.nextInt(LOWER.length())));
        password.append(UPPER.charAt(RANDOM.nextInt(UPPER.length())));
        password.append(DIGITS.charAt(RANDOM.nextInt(DIGITS.length())));
        password.append(SPECIAL.charAt(RANDOM.nextInt(SPECIAL.length())));

        // Completar la contraseña con caracteres aleatorios
        for (int i = 4; i < length; i++) {
            password.append(ALL_CHARS.charAt(RANDOM.nextInt(ALL_CHARS.length())));
        }

        // Mezclar los caracteres para evitar patrones
        return shuffleString(password.toString());
    }

    private static String shuffleString(String input) {
        List<Character> characters = input.chars().mapToObj(c -> (char) c).collect(Collectors.toList());
        Collections.shuffle(characters);
        StringBuilder shuffled = new StringBuilder();
        characters.forEach(shuffled::append);
        return shuffled.toString();
    }
}