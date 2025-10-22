import java.util.Random;

public class Warlock extends CianniCharacterExam {
    boolean skill1 = false; // SHADOW BOLT ready

    public Warlock(String name) {
        super(name, 100, 120, 60);
    }

    //  SHADOW BOLT (costs 30 MP)
    @Override
    public void primary(CianniCharacterExam target) {
        if (mp >= 30) {
            mp -= 30;
            skill1 = true;
            System.out.println(name + " used SHADOW BOLT! Next magic attack will deal 50% more damage.");
        } else {
            System.out.println(name + " tried to use SHADOW BOLT, but doesn’t have enough Mana (needs 30 MP)!");
        }
    }

    //  HEALTHSTONE (costs 40 MP, heals HP and regenerates MP & SP)
    @Override
    public void secondary(CianniCharacterExam target) {
        if (mp >= 40) {
            mp -= 40;

            // Heal 40% of max HP
            int healAmount = (int) (maxHp * 0.40);
            hp = Math.min(maxHp, hp + healAmount);

            // Regenerate 40% of max MP
            int mpRegen = (int) (maxMp * 0.40);
            mp = Math.min(maxMp, mp + mpRegen);

            // Regenerate 30% of max SP
            int spRegen = (int) (maxSp * 0.30);
            sp = Math.min(maxSp, sp + spRegen);

            System.out.println(name + " used HEALTHSTONE and restored " + healAmount + " HP!");
            System.out.println(name + " also regenerates " + mpRegen + " MP and " + spRegen + " SP through dark energy!");
        } else {
            System.out.println(name + " tried to use HEALTHSTONE, but doesn’t have enough Mana (needs 40 MP)!");
        }
    }

    // Basic Attack (costs SP)
    @Override
    public void attack(CianniCharacterExam target) {
        if (sp < 2) {
            System.out.println(name + " tried to attack, but doesn’t have enough Stamina (needs 2 SP)!");
            return;
        }

        sp -= 2;
        int baseDamage = new Random().nextInt(25) + 10;
        int totalDamage = skill1 ? (int) (baseDamage * 1.5) : baseDamage;

        if (target.defending) {
            totalDamage /= 2;
            System.out.println(target.name + " defended and reduced the damage!");
        }

        target.hp -= totalDamage;
        System.out.println(name + " casts a dark spell and deals " + totalDamage + " damage!");

        if (skill1) {
            System.out.println(name + "'s empowered SHADOW BOLT surges with extra power!");
            skill1 = false;
        }
    }

    // Default endTurn behavior
    @Override
    public void endTurn() {
        defending = false;
    }
}
