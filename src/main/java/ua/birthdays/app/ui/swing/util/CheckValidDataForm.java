package ua.birthdays.app.ui.swing.util;

import javax.swing.*;
import java.util.stream.Stream;

public final class CheckValidDataForm {
    private CheckValidDataForm() {
    }

    public static boolean isFieldsEmpty(JDialog dialog, String... fields) {
        if (Stream.of(fields)
                  .anyMatch(String::isEmpty)) {
            JOptionPane.showMessageDialog(dialog,
                    "Please full all fields!",
                    "Try again",
                    JOptionPane.WARNING_MESSAGE);
            return true;
        }
        return false;
    }

    public static boolean isFieldsNumber(JDialog dialog, String... fields) {
        if (Stream.of(fields)
                  .anyMatch(field -> !UtilForm.isNumber(field))) {
            JOptionPane.showMessageDialog(dialog,
                    "Please remove letters from fields where you have to write numbers!",
                    "Try again",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    public static boolean isCheckReminderDistance(JDialog dialog,
                                                  int field,
                                                  int limitationStartField, int limitationEndField,
                                                  String textMessage
    ) {
        if (field < limitationStartField || field > limitationEndField) {
            JOptionPane.showMessageDialog(dialog,
                    textMessage,
                    "Try again",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }
}
