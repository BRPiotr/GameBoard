import java.util.ArrayList;
import java.util.List;

public class GameService {
    public Game startGame(Team homeTeam, Team awayTeam) {
        return new Game(homeTeam, awayTeam);
    }

    public List<Game> getScoreBoard() {
        return new ArrayList<>();
    }

    public Game updateScore(Team homeTeam, Short homeScore, Team awayTeam, Short awayScore) {
        return null;
    }

    public void finishGame(Team homeTeam1, Team awayTeam1) {

    }
}
