import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Display {

    private final int MAX_PLAYER_WIDTH = 18;
    
    private int judytaIndex;
    private int tableWidth;
    private ArrayList<Player> players;
    // private HashMap<ArrayList<Player>, Integer> playersPrintWidth;

    public Display(ArrayList<Player> players) {

        this.players = players;
        this.tableWidth = this.getTableWidth();
        this.judytaIndex = players.indexOf(players.get(players.size() - 1));

    }

    private int getTableWidth() {

        return (players.size() - 1) * MAX_PLAYER_WIDTH;
    }

    public void table(int totalBet) {

        Player judyta = players.get(judytaIndex);
        StringBuilder table = new StringBuilder();

        table.append(this.formattedBot(judyta.getName(), (tableWidth / 2)) + "\n")    // Name
             .append(this.formattedBot("Coins: " + judyta.getCoolcoin(), (tableWidth / 2)) + "\n\n")   // Coins
             .append(String.format("%1$-" + (tableWidth / 2) + "s", " ")).append(" ") //  Tactical space
             .append(this.formattedCards((tableWidth / 2), judyta) + "\n\n")    // Cards
             .append(this.formattedBot("Score: " + judyta.getScore(), (tableWidth / 2)) + "\n\n\n\n")   // Score
             .append(this.formattedBot("Total bet: " + totalBet, (tableWidth / 2)) + "\n\n\n\n");   // Total bet


        table.append(this.playersData());

        System.out.println(table.toString());

    }

    private String playersData() {

        StringBuilder playersData = new StringBuilder();
        ArrayList<Player> humanPlayers = getHumanPlayers();

        ArrayList<String> names = new ArrayList<>();
        ArrayList<Integer> coins = new ArrayList<>();
        ArrayList<Integer> scores = new ArrayList<>();

        PlayerIterator iterator = new PlayerIterator(humanPlayers);

        while (iterator.hasNext()) {
            Player player = iterator.next();
            names.add(player.getName());
            coins.add(player.getCoolcoin());
            scores.add(player.getScore());
        }

        for (String n : names) {
            playersData.append(formattedHuman(n, MAX_PLAYER_WIDTH));
        }

        playersData.append("\n");

        for (Integer c : coins) {
            playersData.append(formattedHuman("Coins: ".concat(c.toString()), MAX_PLAYER_WIDTH));
        }

        playersData.append("\n\n");

        for (Player player : humanPlayers) {
            playersData.append(formattedCards(MAX_PLAYER_WIDTH, player));
        }

        playersData.append("\n\n");

        for (Integer s : scores) {
            playersData.append(formattedHuman("Score: " + s.toString(), MAX_PLAYER_WIDTH));
        }

        return playersData.toString();
    }

    private String formattedBot(String content, int width) {
        
        return String.format("%1$-" + width + "s "
                            + "%2$-" + width + "s"                            
                            , " "
                            , content);

    }

    private String formattedHuman(String content, int width) {

        return String.format("%1$-" + width + "s", content);
    }

    private String formattedCards(int width, Player player) {

        List<Card> cards = player.getHand().getAllCards();

        StringBuilder cardsFormat = new StringBuilder();

        for (Card c : cards) {
            cardsFormat.append(String.format("%1$c", c.getCodePointValue()));
            cardsFormat.append(" ");
        }

        cardsFormat.append(String.format("%1$-" + (width - (cards.size() * 2) - 1) + "s", " "))
                   .append(" ");

        return cardsFormat.toString();
    }

    public ArrayList<Player> getHumanPlayers() {

        Player judyta = players.get(judytaIndex);
        ArrayList<Player> humanPlayers = new ArrayList<>(players);
        humanPlayers.remove(judyta);

        return humanPlayers;
    }

}