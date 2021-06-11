package logic;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

class Dice {
    private static final Random random = new Random();
    static int roll() {
        return 1 + random.nextInt(6);
    }

    static int[] dice_rolls(int attackTroops, int defendTroops) {
        attackTroops--;
        while (attackTroops > 0 && defendTroops > 0) {
            int[] attackCast = attackDice(attackTroops);
            int[] defendCast = defendDice(defendTroops);
            int[] totalTroopsLost = compareCasts(attackCast, defendCast);
            attackTroops -= totalTroopsLost[0];
            defendTroops -= totalTroopsLost[1];
        }
        return new int[]{attackTroops, defendTroops};
    }

    private static int[] attackDice(int units) {
        int[] cast;
        if(units >= 3) {
            cast = new int[]{Dice.roll(), Dice.roll(), Dice.roll()};
        }
        else if(units == 2) {
            cast = new int[]{Dice.roll(), Dice.roll()};
        }
        else {
            cast = new int[]{Dice.roll()};
        }
        Arrays.sort(cast);
        cast = reversed(cast);
        return cast;
    }

    private static int[] defendDice(int units) {
        int[] cast;
        if(units >= 2) {
            cast = new int[]{Dice.roll(), Dice.roll()};
        }
        else {
            cast = new int[]{Dice.roll()};
        }
        Arrays.sort(cast);
        cast = reversed(cast);
        return cast;
    }

    private static int[] compareCasts(int[] attack_cast, int[] defend_cast) {
        int[] totalLost = {0, 0};
        for(int i = 0; i < Math.min(attack_cast.length, defend_cast.length); i++) {
            if(attack_cast[i] > defend_cast[i]) {
                totalLost[1]++;
            }
            else {
                totalLost[0]++;
            }
        }
        return totalLost;
    }

    private static int[] reversed(int[] array) {
        return IntStream.range(0, array.length).map(i -> array[array.length-i-1]).toArray();
    }

    static boolean attackerWins(int[] troopsLeft) {
        return troopsLeft[0] > 0;
    }
}
