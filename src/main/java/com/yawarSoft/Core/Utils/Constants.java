package com.yawarSoft.Core.Utils;

import java.time.Duration;

public final class Constants {

    private Constants() {
    }

    public static Long getTimeToken(){
        Duration expiration = Duration.ofHours(2);
        return System.currentTimeMillis() + expiration.toMillis();
    }
}
