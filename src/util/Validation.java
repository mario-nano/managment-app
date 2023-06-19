package util;

import java.time.LocalDate;

public class Validation {

    public static boolean isEmpty(String input) {
        if (input.equals("")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isEmpty(LocalDate input) {
        if (input.equals("")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean tryParseDouble(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
