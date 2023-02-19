import java.util.List;

public class GameService {

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
        store.updateScore(homeTeam, homeScore, awayTeam, awayScore);
    }

    public void finishGame(Team homeTeam, Team awayTeam) {
        store.finishGame(homeTeam, awayTeam);
    }
}
