package ua.birthdays.app.domain.util;

public class Randomize {
    public static int generateRandomize(int min, int max) {
        return (int) Math.floor(Math.random() * (max - min + 1) + min);
    }
}
