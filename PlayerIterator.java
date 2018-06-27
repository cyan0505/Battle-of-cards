import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

public class PlayerIterator implements Iterator<Player> {

    private int index;
    private List<Player> players;

    public PlayerIterator(ArrayList<Player> players) {
        this.players = players;
    }

    public boolean hasNext() {
        return index < players.size();
    }

    public Player next() {
        return players.get(index++);
    }
}