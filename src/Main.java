import java.util.Random;

public class Main {
    public static int bossHealth = 1000;
    public static int bossDamage = 60;
    public static String bossDefence;
    public static int[] heroesHealth = {200, 220, 210, 200,};
    public static int[] heroesDamage = {35, 20, 15, 0,};
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "Medic",};
    public static int roundNumber = 0;
    public static int medicHealingAmount = 85;


    public static void main(String[] args) {
        printStatistics();
        while (!isGameOver()) {
            playRound();
        }
    }

    public static void playRound() {
        roundNumber++;
        chooseBossDefence();
        medicHealing();
        bossAttack();
        heroesAttack();
        printStatistics();
    }


    public static int chooseMedicHealing() {
        Random random = new Random();
        int medicIndex;
        do {
            medicIndex = random.nextInt(heroesHealth.length);
        } while (heroesAttackType[medicIndex].equals("Medic"));
        return medicIndex;
    }

    public static void medicHealing() {
        int medicHealingType = chooseMedicHealing();
        if ((heroesHealth[medicHealingType] > 0) && (heroesHealth[medicHealingType] < 100) &&
                !heroesAttackType[medicHealingType].equals("Medic") && (heroesHealth[3] > 0)) {
            heroesHealth[medicHealingType] += medicHealingAmount;
            System.out.println("Medic healed " + heroesAttackType[medicHealingType] + " for " + medicHealingAmount + " health.");
        }
    }


    public static void chooseBossDefence() {
        Random random = new Random();
        int randomIndex;
        do {
            randomIndex = random.nextInt(heroesAttackType.length);
        } while (heroesAttackType[randomIndex].equals("Medic"));
        bossDefence = heroesAttackType[randomIndex];
    }

    public static void bossAttack() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                if (heroesHealth[i] - bossDamage < 0) {
                    heroesHealth[i] = 0;
                } else {
                    heroesHealth[i] -= bossDamage; // heroesHealth[i] = heroesHealth[i] - bossDamage;
                }
            }
        }
    }

    public static void heroesAttack() {
        for (int i = 0; i < heroesDamage.length; i++) {
            int damage = 0;
            if (heroesHealth[i] > 0 && bossHealth > 0 && !heroesAttackType[i].equals("Medic")) {
                damage = heroesDamage[i];
                if (heroesAttackType[i] == bossDefence) {
                    Random random = new Random();
                    int coeff = random.nextInt(9) + 2; // 2,3,4,5,6,7,8,9,10
                    damage = heroesDamage[i] * coeff;
                    System.out.println("Critical damage: " + damage);
                }
            }

            if (bossHealth - damage < 0) {
                bossHealth = 0;
            } else {
                bossHealth -= damage; // bossHealth = bossHealth - damage;
            }
        }
    }





    public static void printStatistics () {
        System.out.println("ROUND " + roundNumber + " ---------------");
        /*String defence;
        if (bossDefence == null) {
            defence = "No defence";
        } else {
            defence = bossDefence;
        }*/
        System.out.println("Boss health: " + bossHealth + " damage: " + bossDamage + " defence: " +
                (bossDefence == null ? "No defence" : bossDefence));
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] + " health: " + heroesHealth[i] + " damage: " + heroesDamage[i]);
        }
    }

    public static boolean isGameOver () {
        if (bossHealth <= 0) {
            System.out.println("Heroes win!!!");
            return true;
        }
        /*if (heroesHealth[0] <= 0 && heroesHealth[1] <= 0 && heroesHealth[2] <= 0) {
            System.out.println("Boss win!!!");
            return true;
        }
        return false;*/
        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss win!!!");
        }
        return allHeroesDead;
    }
}