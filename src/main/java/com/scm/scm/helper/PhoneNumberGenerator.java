package com.scm.scm.helper;
import java.util.concurrent.ThreadLocalRandom;

public class PhoneNumberGenerator {

    public static String generateUnique10DigitNumber() {
        long number = ThreadLocalRandom.current()
                .nextLong(1_000_000_000L, 10_000_000_000L); // 10 digits
        return String.valueOf(number);
    }
}
