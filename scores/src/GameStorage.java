import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class GameStorage {

    Logger LOG = LoggerFactory.getLogger(GameStorage.class);
    private static GameStorage instance;

    private static volatile LinkedList<Game> games;

    private GameStorage() {

    }

    public static GameStorage getInstance() {
        if (instance == null) {
            synchronized (GameStorage.class) {
                instance = new GameStorage();
                GameStorage.games = new LinkedList<>();
            }
        }
        return instance;
    }

    public void addGame(Game game) {
        if (!isGameActive(game)) {
            games.add(game);
        } else {
            LOG.warn(() -> String.format("Cannot start already active Game %s : %s", game.getHomeTeam(), game.getAwayTeam()));
        }
    }

    private boolean isGameActive(Game game) {
        return isGameActive(game.getHomeTeam(), game.getAwayTeam());
    }

    private boolean isGameActive(Team homeTeam, Team awayTeam) {
        return games.stream().anyMatch(g ->
                g.getHomeTeam().equals(homeTeam) && g.getAwayTeam().equals(awayTeam) && g.isActive()
        );
    }

    public List<Game> listActiveGames() {
        Comparator<Game> cumulativeScoreComparator =
                (o1, o2) -> (o2.getAwayScore() + o2.getHomeScore()) - (o1.getAwayScore() + o1.getHomeScore());
        Comparator<Game> startDateDescComparator =
                (o1, o2) -> o2.getStartDateTime().compareTo(o1.getStartDateTime());

        return games.stream().filter(Game::isActive).sorted(
                cumulativeScoreComparator.thenComparing(startDateDescComparator)).collect(Collectors.toList());
    }

    public void finishGame(Team homeTeam, Team awayTeam) {
        games.forEach(game -> {
            if (game.getHomeTeam().equals(homeTeam) && game.getAwayTeam().equals(awayTeam) && game.isActive()) {
                game.setActive(false);
            }
        });
    }

    public void updateScore(Team homeTeam, Short homeScore, Team awayTeam, Short awayScore) {
        games.forEach(game -> {
            if (game.getHomeTeam().equals(homeTeam) && game.getAwayTeam().equals(awayTeam) && game.isActive()) {
                game.setHomeScore(homeScore);
                game.setAwayScore(awayScore);
            }
        });
    }
}
