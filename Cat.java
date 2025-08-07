public class Cat extends Pet {
    private int miceCaught;

    public Cat(String name, double health, int painLevel, int miceCaught) {
        super(name, health, painLevel);
        if (miceCaught < 0) {
            this.miceCaught = 0;
        } else {
            this.miceCaught = miceCaught;
        }
    }

    public Cat(String name, double health, int painLevel) {
        this(name, health, painLevel, 0);
    }

    public int getMiceCaught() {
        return miceCaught;
    }

    @Override
    public int treat() {
        heal();
        double time;
        if (miceCaught < 4) {
            time = (getPainLevel() * 2.0) / getHealth();
        } else if (miceCaught <= 7) {
            time = getPainLevel() / getHealth();
        } else {
            time = getPainLevel() / (getHealth() * 2.0);
        }
        return (int) Math.ceil(time);
    }

    @Override
    public void speak() {
        super.speak();
        String meow = (getPainLevel() > 5) ? "MEOW" : "meow";
        for (int i = 0; i < miceCaught; i++) {
            System.out.print(meow);
            if (i < miceCaught - 1) {
                System.out.print(" ");
            }
        }
        System.out.println();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Cat)) {
            return false;
        }
        Cat other = (Cat) o;
        return super.equals(other) && this.miceCaught == other.miceCaught;
    }
}
