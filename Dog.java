public class Dog extends Pet {
    private double droolRate;

    public Dog(String name, double health, int painLevel, double droolRate) {
        super(name, health, painLevel);
        if (droolRate <= 0) {
            this.droolRate = 0.5;
        } else {
            this.droolRate = droolRate;
        }
    }

    public Dog(String name, double health, int painLevel) {
        this(name, health, painLevel, 5.0);
    }

    public double getDroolRate() {
        return droolRate;
    }

    @Override
    public int treat() {
        heal();
        double time;
        if (droolRate < 3.5) {
            time = (getPainLevel() * 2.0) / getHealth();
        } else if (droolRate <= 7.5) {
            time = getPainLevel() / getHealth();
        } else {
            time = getPainLevel() / (getHealth() * 2.0);
        }
        return (int) Math.ceil(time);
    }

    @Override
    public void speak() {
        super.speak();
        String bark = (getPainLevel() > 5) ? "BARK" : "bark";
        for (int i = 0; i < getPainLevel(); i++) {
            System.out.print(bark);
            if (i < getPainLevel() - 1) {
                System.out.print(" ");
            }
        }
        System.out.println();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Dog)) {
            return false;
        }
        Dog other = (Dog) o;
        return super.equals(other) && this.droolRate == other.droolRate;
    }
}
