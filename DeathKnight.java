import java.util.Random;

public class DeathKnight extends CianniCharacterExam {
    boolean skill1 = false; // DEATH STRIKE active
    int skill2 = 0;         // ICEBOUND FORTITUDE turns remaining

    public DeathKnight(String name) {
        super(name, 140, 40, 100);
    }

    // Skill 1: DEATH STRIKE (costs 30 SP)
    @Override
    public void primary(CianniCharacterExam target) {
        if (sp >= 30) {
            sp -= 30;
            skill1 = true;
            System.out.println(name + " used DEATH STRIKE! The next PHYSICAL ATTACK will HEAL them for 10% of their MAX HEALTH.");
        } else {
            System.out.println(name + " tried to use DEATH STRIKE, but doesn’t have enough STAMINA (needs 30 SP)!");
        }
    }

    // Skill 2: ICEBOUND FORTITUDE (costs 50 SP, grants 35% SP regen each turn)
    @Override
    public void secondary(CianniCharacterExam target) {
        if (sp >= 50) {
            sp -= 50;
            skill2 = 2; // lasts for 2 turns
            System.out.println(name + " used ICEBOUND FORTITUDE! DAMAGE taken is REDUCED by 30% for 2 turns and REGENERATES 35% of MAX SP each turn!");
        } else {
            System.out.println(name + " tried to use ICEBOUND FORTITUDE, but doesn’t have enough STAMINA (needs 50 SP)!");
        }
    }

    // Basic Attack (drains 2 SP, heals if DEATH STRIKE is active)
    @Override
    public void attack(CianniCharacterExam target) {
        if (sp < 2) {
            System.out.println(name + " tried to ATTACK, but doesn’t have enough STAMINA (needs 2 SP)!");
            return;
        }

        sp -= 2;

        int damage = new Random().nextInt(15) + 15; // 15–30 damage range

        if (target.defending) {
            damage /= 2;
            System.out.println(target.name + " DEFENDED and REDUCED the damage!");
        }

        target.hp -= damage;
        System.out.println(name + " slashes " + target.name + " for " + damage + " DAMAGE!");

        if (skill1) {
            int heal = (int)(maxHp * 0.10);
            hp = Math.min(maxHp, hp + heal);
            System.out.println(name + " channels DEATH STRIKE and HEALS for " + heal + " HP!");
            skill1 = false;
        }
    }

    // Override endTurn to handle Icebound Fortitude regeneration and expiration
    @Override
    public void endTurn() {
        defending = false;

        if (skill2 > 0) {
            // Regenerate 35% of max SP each turn while active
            int regen = (int)(maxSp * 0.35);
            sp = Math.min(maxSp, sp + regen);
            System.out.println(name + " regenerates " + regen + " SP from ICEBOUND FORTITUDE!");

            skill2--;
            if (skill2 == 0) {
                System.out.println(name + "'s ICEBOUND FORTITUDE has worn off! The SP regeneration and damage reduction fade.");
            }
        }
    }
}