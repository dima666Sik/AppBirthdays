package ua.birthdays.app.ui.swing.util;

public class UtilForm {
    public static boolean isNumber(String strValueFromField) {
        if (strValueFromField == null || strValueFromField.isEmpty()) return false;
        for (int i = 0; i < strValueFromField.length(); i++) {
            if (!Character.isDigit(strValueFromField.charAt(i))) return false;
        }
        return true;
    }

}
