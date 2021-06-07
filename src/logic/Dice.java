package logic;

import java.util.Random;

class Dice {
    private static final Random random = new Random();
    public static int roll() {
        return 1 + random.nextInt(6);
    }
}
