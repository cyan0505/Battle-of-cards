public abstract class Player {
    private String name;
    private int coolcoin;
    private Pile hand;
    private int score;
    private boolean bust = false;
    private boolean winner = false;
    private boolean pass = false;

    
    public Player(String name, int cash) {
        this.name = name;
        this.coolcoin = cash;
        this.hand = new Pile();
    }

    public String getName() {
        return this.name;
    }

    public int getCoolcoin() {
        return this.coolcoin;
    }

    public Pile getHand() {
        return this.hand;
    }

    public int getScore() {
        return this.score;
    }

    public boolean getBust() {
        return this.bust;
    }

    public boolean getWinner() {
        return this.winner;
    }

    public boolean getPass() {
        return this.pass;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCoolcoin(int cash) {
        this.coolcoin = cash;
    } 

    public void setScore(int score) {
        this.score = score;
    } 

    public void setScoreEqualToCardsValue() {
        this.score = hand.givePiletotalScore();
    }

    public void setBust(boolean bust) {
        this.bust = bust;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    } 

    public void setPass(boolean pass) {
        this.pass = pass;
    }

    public void betCoins(int cash) {
        setCoolcoin(this.coolcoin-cash);
    }

    public void takeCard(Pile deck) {
        Card cardToHit = deck.getTopCard();
        cardToHit.changePileToDest(this.getHand());
        cardToHit.flip();
    }

    public abstract void pass();

    public abstract boolean isPassed();

}