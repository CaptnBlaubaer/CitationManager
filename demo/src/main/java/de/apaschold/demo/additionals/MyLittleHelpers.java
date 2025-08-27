package de.apaschold.demo.additionals;

public class MyLittleHelpers {

    private MyLittleHelpers() {
    }

    // convert Functions
    public static int convertStringInputToInteger(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static int convertStringInputToInteger(String str, int defaultValue) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
