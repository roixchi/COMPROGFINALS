import java.util.Random;
import java.util.Scanner;

public class CianniMainExam {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random rand = new Random();

        System.out.println("Choose your class:");
        System.out.println("1. Death Knight");
        System.out.println("2. Warlock");
        System.out.print("Enter choice: ");
        int choice = sc.nextInt();

        CianniCharacterExam player, enemy;

        if (choice == 1) {
            player = new DeathKnight("Player Death Knight");
            enemy = new Warlock("Enemy Warlock");
        } else {
            player = new Warlock("Player Warlock");
            enemy = new DeathKnight("Enemy Death Knight");
        }

        // Player starts with 1 of each potion
        int healingPotions = 1;
        int manaPotions = 1;

        System.out.println("\n--- Battle Start! ---");

        while (player.isAlive()) {
            // Display current stats
            System.out.println("\n" + player.getStatus());
            System.out.println(enemy.getStatus());

            // Player's turn
            System.out.println("\nYour turn:");
            System.out.println("1. Attack");
            System.out.println("2. Defend");
            System.out.println("3. Use Item");
            System.out.println("4. Primary Skill");
            System.out.println("5. Secondary Skill");
            System.out.print("Choose action: ");
            int action = sc.nextInt();

            switch (action) {
                case 1 -> player.attack(enemy);
                case 2 -> player.defend();
                case 3 -> {
                    System.out.println("1. Healing Potion (" + healingPotions + " left)");
                    System.out.println("2. Mana Potion (" + manaPotions + " left)");
                    int itemChoice = sc.nextInt();
                    if (itemChoice == 1 && healingPotions > 0) {
                        player.useItem("Healing");
                        healingPotions--;
                    } else if (itemChoice == 2 && manaPotions > 0) {
                        player.useItem("Mana");
                        manaPotions--;
                    } else {
                        System.out.println("No potions of that type left!");
                    }
                }
                case 4 -> player.primary(enemy);
                case 5 -> player.secondary(enemy);
                default -> System.out.println("Invalid choice!");
            }

            // Enemy defeated check
            if (!enemy.isAlive()) {
                System.out.println(enemy.name + " was defeated!");
                String drop = rand.nextBoolean() ? "Healing" : "Mana";
                if (drop.equals("Healing")) healingPotions++;
                else manaPotions++;
                System.out.println("You obtained a " + drop + " Potion!\n");

                enemy = (player instanceof DeathKnight)
                        ? new Warlock("Enemy Warlock")
                        : new DeathKnight("Enemy Death Knight");
                continue;
            }

            // Enemy’s turn
            System.out.println("\nEnemy's turn!");
            int enemyMove = rand.nextInt(5);
            switch (enemyMove) {
                case 0 -> enemy.attack(player);
                case 1 -> enemy.defend();
                case 2 -> enemy.primary(player);
                case 3 -> enemy.secondary(player);
                default -> System.out.println(enemy.name + " hesitates...");
            }

            // Player defeated check
            if (!player.isAlive()) {
                System.out.println("\nYou were defeated!");
                break;
            }

            // ✅Round summary BEFORE regen
            System.out.println("\n--- Round Summary ---");
            System.out.println(player.getStatus());
            System.out.println(enemy.getStatus());

            // End of round cleanup (regen, reset defend, etc.)
            player.endTurn();
            enemy.endTurn();
        }
    }
}