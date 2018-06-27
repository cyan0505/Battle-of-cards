import java.util.*;

public class Card {

    private static final int CODE_POINT_BACK = 127136;
    private static final int STARTING_CODE_POINT_DIAMOND = 127168;
    private static final int STARTING_CODE_POINT_HEART = 127152;
    private static final int STARTING_CODE_POINT_PEAK = 127136;
    private static final int STARTING_CODE_POINT_CLUB = 127184;

    private Suits suit;
    private int rank;
    private boolean isFaceDown;
    private int value;
    private Pile pile;

    public Card(Suits suit, int rank, Pile pile){
        this.suit = suit;
        this.rank = rank;
        this.isFaceDown = true;
        setValue();
        this.pile = pile;
    }
    public void flip(){
        if(isFaceDown == true){
            this.isFaceDown = false;
        }
        else{
            this.isFaceDown = true;
        }
    }
    public boolean isFaceDown(){
        return this.isFaceDown;
    }
    private void setValue(){
        if(rank < 10){
            this.value = this.rank;
        }
        else if(rank >= 10 && rank < 14){
                this.value = 10;
        }
        else{
            this.value = 11;
        }
    }
    public void setFaceDown(boolean faceDown) {
        this.isFaceDown = faceDown;
    }
    public int getValue(){
        return this.value;
    }
    public String toString(){
        //String cartToPrint = "";
        return "" + this.rank + this.getIcon() + " ";
    }
    public int getCodePointValue() {

        int codePointValue;

        if (this.isFaceDown) {
            return CODE_POINT_BACK;
        }

        else {

            if (this.suit.equals(Suits.PEAK)) {
                codePointValue = Suits.PEAK.startingCodePointValue;
                codePointValue = addEqualValue(codePointValue);
            }

            else if (this.suit.equals(Suits.HEART)) {
                codePointValue = Suits.HEART.startingCodePointValue;
                codePointValue = addEqualValue(codePointValue);
            }

            else if (this.suit.equals(Suits.DIAMOND)) {
                codePointValue = Suits.DIAMOND.startingCodePointValue;
                codePointValue = addEqualValue(codePointValue);
            }

            else {
                codePointValue = Suits.CLUB.startingCodePointValue;
                codePointValue = addEqualValue(codePointValue);
            }

            return codePointValue;

        }
    }

    private int addEqualValue(int actualValue) {

        if (this.rank == 14) {
            actualValue += 1;
        }
        else {
            actualValue += this.rank;
        }

        return actualValue;
    }

    public void changePileToDest(Pile destPile) {
        destPile.addCard(this);
        pile.removeCard(this);
    }

    public enum Suits {
        
        DIAMOND (STARTING_CODE_POINT_DIAMOND),
        HEART (STARTING_CODE_POINT_HEART),
        PEAK (STARTING_CODE_POINT_PEAK),
        CLUB (STARTING_CODE_POINT_CLUB);

        public final int startingCodePointValue;

        private Suits(int startingCodePointValue) {
            this.startingCodePointValue = startingCodePointValue;
        }
    }

    public String getIcon() {
        Map<Suits, String> suitIcon = new HashMap<Suits, String>();
 
        suitIcon.put(Suits.DIAMOND, "\u2666");
        suitIcon.put(Suits.HEART, "\u2665");
        suitIcon.put(Suits.PEAK, "\u2660");
        suitIcon.put(Suits.CLUB, "\u2663");

        //karo "\u2666"
        //kier "\u2665"
        //pik "\u2660"
        //trefl "\u2663"
        return suitIcon.get(this.suit);
    }
    
    public boolean equals (Card card) {
        if (this.hashCode() == card.hashCode()) {
            if (this.suit == card.suit) {
                if (this.rank == card.rank) {
                    if (this.isFaceDown == card.isFaceDown) {
                        return true;
                    } else return false;
                } else return false;
            } else return false;
        } else return false;
    }

    @Override
    public int hashCode() {
        int faceDownValue = this.isFaceDown ? 1 : -1;
        int hashCodeValue = this.suit.hashCode() * this.rank * faceDownValue;
        return hashCodeValue;
    }
}