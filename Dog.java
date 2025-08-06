public class Dog extends Pet{
    private double droolRate;
    private int test;

    public Dog (String name, double health, int painLevel, double droolRate){
        super(name, health, painLevel);
        if(droolRate <= 0) {
            this.droolRate = 0.5;
        }
        else {
            this.droolRate = droolRate;
        }
        }
        public double getDroolRate() {
            return droolRate;
        }
        public int treat(){
            super.heal();
            return test;
        }
}