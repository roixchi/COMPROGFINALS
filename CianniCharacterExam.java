import java.util.Random;

public abstract class CianniCharacterExam {
    protected String name;
    protected int hp, mp, sp;
    protected int maxHp, maxMp, maxSp;
    protected boolean defending = false;

    public CianniCharacterExam(String name, int hp, int mp, int sp) {
        this.name = name;
        this.hp = this.maxHp = hp;
        this.mp = this.maxMp = mp;
        this.sp = this.maxSp = sp;
    }

    public boolean isAlive() {
        return hp > 0;
    }

    public void useItem(String itemType) {
        if (itemType.equalsIgnoreCase("Healing")) {
            int heal = (int) (maxHp * 0.30);
            hp = Math.min(maxHp, hp + heal);
            System.out.println(name + " used a Healing Potion and recovered " + heal + " HP!");
        } else if (itemType.equalsIgnoreCase("Mana")) {
            int mana = (int) (maxMp * 0.30);
            mp = Math.min(maxMp, mp + mana);
            System.out.println(name + " used a Mana Potion and recovered " + mana + " MP!");
        }
    }

    //  Basic Attack: costs 2 SP
    public void attack(CianniCharacterExam target) {
        if (sp < 2) {
            System.out.println(name + " tried to attack, but doesn’t have enough Stamina (needs 2 SP)!");
            return;
        }

        sp -= 2;
        int damage = new Random().nextInt(10) + 10;
        if (target.defending) {
            damage /= 2;
            System.out.println(target.name + " defended and reduced the damage!");
        }

        target.hp -= damage;
        System.out.println(name + " attacks " + target.name + " for " + damage + " damage!");
    }

    //  Defend: costs 3 SP
    public void defend() {
        if (sp < 3) {
            System.out.println(name + " tried to defend, but doesn’t have enough Stamina (needs 3 SP)!");
            return;
        }

        sp -= 3;
        defending = true;
        System.out.println(name + " takes a defensive stance! Incoming damage will be reduced this turn.");
    }

    public void endTurn() {
        defending = false;
    }

    public String getStatus() {
        return String.format("%s — HP: %d/%d | MP: %d/%d | SP: %d/%d",
                name, hp, maxHp, mp, maxMp, sp, maxSp);
    }

    public abstract void primary(CianniCharacterExam target);
    public abstract void secondary(CianniCharacterExam target);
}
