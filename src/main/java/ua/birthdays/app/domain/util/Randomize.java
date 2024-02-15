package ua.birthdays.app.domain.util;

public final class Randomize {
    private Randomize() {
    }

    public static int generateRandomize(int min, int max) {
        double randomValue = Math.random();
        return (int) Math.floor(randomValue * (max - min + 1) + min);
    }
}
