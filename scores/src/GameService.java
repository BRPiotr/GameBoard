import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

import java.util.List;

public class GameService {

    Logger LOG = LoggerFactory.getLogger(GameService.class);

    private final GameStorage store = GameStorage.getInstance();

    public Game startGame(Team homeTeam, Team awayTeam) {
        Game newGame = new Game(homeTeam, awayTeam);
        store.addGame(newGame);
        return newGame;
    }

    public List<Game> getScoreBoard() {
        return store.listActiveGames();
    }

    public void updateScore(Team homeTeam, Short homeScore, Team awayTeam, Short awayScore) {
        if (homeScore >= 0 && awayScore >= 0) {
            store.updateScore(homeTeam, homeScore, awayTeam, awayScore);
        } else {
            LOG.warn(() -> String.format("prevented from updating negative score for %s: %s - %s: %s",
                    homeTeam, homeScore, awayTeam, awayScore));
        }
    }

    public void finishGame(Team homeTeam, Team awayTeam) {
        store.finishGame(homeTeam, awayTeam);
    }
}
