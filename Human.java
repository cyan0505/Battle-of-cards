public class Human extends Player {

    public Human(String name) {
        super(name, 100);
    }
    
    @Override
    public void pass() {
     
    }

    @Override
    public boolean isPassed() { 
        int decision = 0;
        while (decision != 1 && decision != 2) {
            decision = Input.getInt("What do you want to do?\n 1. -> pass\n 2. -> hit card");
        }
        return decision == 1;
    }
}