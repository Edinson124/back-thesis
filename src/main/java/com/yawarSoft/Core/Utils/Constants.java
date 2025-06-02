package com.yawarSoft.Core.Utils;

import java.time.Duration;

public final class Constants {

    private Constants() {
    }

    public static Long getTimeToken(){
        Duration expiration = Duration.ofHours(2);
        return System.currentTimeMillis() + expiration.toMillis();
    }
    // Tiempo mínimo entre donaciones para hombres (4 meses)
    public static final int DONATION_INTERVAL_MALE_MONTHS = 3;

    // Tiempo mínimo entre donaciones para mujeres (3 meses)
    public static final int DONATION_INTERVAL_FEMALE_MONTHS = 4;
}
