import java.security.Principal;
import java.util.*;

//import Card.Suits;

public class Game {
    private ArrayList<Player> players;
    private Pile deck;
    private int cashPool;
    private Display display;

    public Game(ArrayList<Player> players) {
        this.players = new ArrayList<>(players);
        cashPool = 0;
        deck = new Pile();
        createDeck();
        display = new Display(players);

    }

    public void launch() {

        int moneyChecker = 1;
        
        System.out.println("\033[H\033[2J");
        while (moneyChecker != 0) {
            newRound();

            for (Player player : players) {

                gameLogic(player);
                System.out.println("\033[H\033[2J");
                display.table(cashPool);

            }
            checkHighestScore();
            giveCoolcoinsToWinner();

            for(Player player : players){
                moneyChecker = moneyChecker*player.getCoolcoin();
            }

            
        }
    }

    private void placingBets(){
        Iterator<Player> playerIterator = players.iterator();
        playerIterator.forEachRemaining(player -> {

            display.table(cashPool);
            if(player.equals(players.get(players.size()-1))){
                cashPool = cashPool*2;
                System.out.println("Judyta doubles Cash Pool");
            }
            else{
                int cash = betCondition(player);
                player.betCoins(cash);
                cashPool += cash;
            }

            System.out.println("\033[H\033[2J");
        });            
    }

    private int getInput(String text) {
        System.out.println(text);
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        try {
            choice = scanner.nextInt();
        } catch (Exception e) {
            System.out.println("This isn't a number!");
        }
        return choice;
    }

    private void createDeck(){
        for (Card.Suits suit : Card.Suits.values()) {
            for (int j=2; j<15; j++){
                deck.addCard(new Card(suit, j, deck));
                // System.out.println("rank = " + i + "suit = " + j);
            }
        }
    }

    public void newRound(){
        
        this.cashPool = 0;
        deck.clear();
        for (Player player : players) {
            player.getHand().clear();
            player.setScoreEqualToCardsValue();
            player.setBust(false);
            player.setWinner(false);
            player.setPass(false);
        }
        createDeck();
        shuffleDeck();
        dealCards();
        placingBets();
        display.table(cashPool);

    }

    private void dealCards(){
        Iterator<Player> playerIterator = players.iterator();
        playerIterator.forEachRemaining(player -> {
            if(player.equals(players.get(players.size()-1))){
                dealToAI(player);
            }
            else {
                dealToPlayer(player);
            }
            // System.out.println(deck.getAllCards().size());
        });
    }

    private void dealToPlayer(Player player){
        player.getHand().addCard(deck.getTopCard());
        player.getHand().getTopCard().flip();
        deck.removeCard(deck.getTopCard());
        player.getHand().addCard(deck.getTopCard());
        player.getHand().getTopCard().flip();
        deck.removeCard(deck.getTopCard());
        player.setScore(player.getHand().givePiletotalScore());
    }

    private void dealToAI(Player player){
        player.getHand().addCard(deck.getTopCard());
        player.getHand().getTopCard().flip();
        deck.removeCard(deck.getTopCard());
        player.getHand().addCard(deck.getTopCard());
        deck.removeCard(deck.getTopCard());
        player.setScore(player.getHand().getAllCards().get(0).getValue());
    }

    private void shuffleDeck(){
        Collections.shuffle(deck.getAllCards());
    }

    private void checkHighestScore(){
        ArrayList<Integer> scoreTable = new ArrayList<>();
        for (Player player : players){
            if (player.getBust()==false){
                scoreTable.add(player.getScore());
            }
        }
        Collections.sort(scoreTable);
        int highestScore = scoreTable.get(scoreTable.size()-1);
        for (Player player : players){
            if (player.getScore()==highestScore){
                player.setWinner(true);
            }
        }
    }

    public Pile getDeck(){
        return this.deck;
    }

    public void setDeck(Pile pile){
        this.deck = pile;
    }

    private void giveCoolcoinsToWinner(){
        ArrayList<Player> winnerList = new ArrayList<>();
        for(Player player : players){
            if(player.getWinner()==true){
                winnerList.add(player);
            }
        }
        System.out.println(winnerList.get(0).getName());
        for (Player player : winnerList){
                player.setCoolcoin(player.getCoolcoin()+cashPool/winnerList.size());
                players.get(players.size() - 1).getHand().setAllCardsFaceUp();
                System.out.println("\nWinner is " + player.getName());
                Input.getString("\nPress any key to continue . . .");
                System.out.println("\033[H\033[2J");
        }
    }

    public int getCashPool() {
        return cashPool;
    }

    public void setCashPool(int cash) {
        cashPool = cash;
    }
    private void clearTable(){
        
        for (Player player : players) {
            clearPlayerPile(player);
            player.setScore(0);
        }
    }

    public void clearPlayerPile(Player player){

        List<Card> cards = player.getHand().getAllCards();
        for (Card card : cards) {
            card.changePileToDest(deck);
        }
    }

    private void gameLogic(Player player){

        System.out.println("\n" + player.getName() + "'s turn!");
        if(player.getName()!="Judyta"){
            handleHumanTurn(player);
        } else {
            handleJudytaBot(player);
        }

    }

    private void handleJudytaBot(Player player) {
        while(player.getBust()==false && player.getPass()==false) {
            player.getHand().getTopCard().flip();
            player.setScore(player.getHand().givePiletotalScore());
            System.out.println(player.getScore());
            int botScore = player.getScore();

            if (botScore > 21) {
                player.setBust(true);
                System.out.println(player.getName() + " busted!");
                
            } else if (botScore >= 20) {
                player.setPass(true);
                System.out.println(player.getName() + " passed!");
            } else {
                Player playerWithHighestScore = playerWithHighestScore();
                if (player.getScore() > playerWithHighestScore.getScore()) {
                    player.setPass(true);
                    System.out.println(player.getName() + " passed!");
                } else {
                    player.takeCard(deck);
                    player.setScore(player.getHand().givePiletotalScore());
                    System.out.println(player.getName() + " Hit a card");
                }

            } 
        }
    }

    private void handleHumanTurn(Player player){
        while(player.getBust()==false && player.getPass()==false){
            int choice = getInput("1. Hit me!\n2. Pass!");
            switch (choice) {
                case 1:
                    player.takeCard(deck);
                    player.setScore(player.getHand().givePiletotalScore());
                    System.out.println("\033[H\033[2J");
                    display.table(cashPool);
                    System.out.println(player.getScore());
                    if(player.getScore()>21){
                        player.setBust(true);
                        player.setScore(0);
                        System.out.println(player.getName() + " busted! - Your score was too high");
                    }
                    break;
                case 2:
                    player.setPass(true);
                    System.out.println(player.getName() + " passed!");
                    break;
            }
        }   
    }

    private Player playerWithHighestScore() {

        ArrayList<Player> humanPlayers = display.getHumanPlayers();
        PlayerIterator playerIterator = new PlayerIterator(humanPlayers);
        Player playerWithHighestScore = null;
        int highestScore = 0;
        do {
            Player player = playerIterator.next();
            if (player.getScore() >= highestScore) {
                highestScore = player.getScore();
                playerWithHighestScore = player;
                }
        } while (playerIterator.hasNext());
        return playerWithHighestScore;
    }
    private int betCondition(Player player){

        while(true){
            int cash = getInput("\nGive bet! ");
            if(cash > player.getCoolcoin()){
                System.out.println("Not enough money");
            }
            else if(cash < 0){
                System.out.println("Wrong number ");
            }
            else if(cash == 0);
            else{
                return cash;
            }
        }   
    }
}
