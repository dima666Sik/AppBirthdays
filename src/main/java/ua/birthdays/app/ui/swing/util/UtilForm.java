package ua.birthdays.app.ui.swing.util;

public final class UtilForm {
    private UtilForm() {
    }

    /**
     * Checks if the passed string is a number.
     *
     * @param strValueFromField The string to check for a numeric value.
     * @return true if the passed string is a number; false if the string is not a number or is empty.
     */
    public static boolean isNumber(String strValueFromField) {
        if (strValueFromField == null || strValueFromField.isEmpty()) return false;
        for (int i = 0; i < strValueFromField.length(); i++) {
            if (!Character.isDigit(strValueFromField.charAt(i))) return false;
        }
        return true;
    }

}
