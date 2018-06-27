import java.util.List;
import java.util.ArrayList;

public class Pile {

    private List<Card> cards;

    public Pile() {
        cards = new ArrayList<>();
    }

    public Card getTopCard() {
        return cards.get(cards.size() - 1);
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public void removeCard(Card card) {
        cards.remove(card);
    }
    public List<Card> getAllCards(){
        return cards;
    }

    public void clear() {
        cards.clear();
    }

    public String toString() {
        
        StringBuilder cardsInPile = new StringBuilder();

        if (cards.isEmpty()) {
            return "empty";
        }

        for (Card c : cards) {
            cardsInPile.append(c + " ");
        }

        return cardsInPile.toString();
    }
    public int getSize(){
        return cards.size();
    }

    public int givePiletotalScore(){
        int pileScore = 0;
        for(Card card : cards){
            pileScore += card.getValue();
        }
        return pileScore;
    }

    public void setAllCardsFaceUp() {
        for (Card card : cards) {
            if (card.isFaceDown()) {
                card.setFaceDown(false);
            }
        }
    }
}