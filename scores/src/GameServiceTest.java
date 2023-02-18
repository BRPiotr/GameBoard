import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static java.lang.Thread.sleep;
import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;

public class GameServiceTest {

    private static final Short SCORE_0 = 0;
    private static final Short SCORE_1 = 1;

    private static final Team HOME_TEAM1 = new Team("homeTeam1");
    private static final Team HOME_TEAM2 = new Team("homeTeam2");
    private static final Team HOME_TEAM3 = new Team("homeTeam3");
    private static final Team HOME_TEAM4 = new Team("homeTeam4");
    private static final Team HOME_TEAM5 = new Team("homeTeam5");
    private static final Team HOME_TEAM6 = new Team("homeTeam6");
    private static final Team HOME_TEAM7 = new Team("homeTeam7");
    private static final Team HOME_TEAM8 = new Team("homeTeam8");

    private static final Team AWAY_TEAM1 = new Team("awayTeam1");
    private static final Team AWAY_TEAM2 = new Team("awayTeam2");
    private static final Team AWAY_TEAM3 = new Team("awayTeam3");
    private static final Team AWAY_TEAM4 = new Team("awayTeam4");
    private static final Team AWAY_TEAM5 = new Team("awayTeam5");
    private static final Team AWAY_TEAM6 = new Team("awayTeam6");
    private static final Team AWAY_TEAM7 = new Team("awayTeam7");
    private static final Team AWAY_TEAM8 = new Team("awayTeam8");

    private GameService service;

    @Before
    public void setUp() {
        service = new GameService();
    }

    @Test
    public void shouldStartGameAndInitialiseWithScoreStartDateAndActive() {
        Game result = service.startGame(HOME_TEAM1, AWAY_TEAM1);
        assertNotNull(result);
        assertEquals(HOME_TEAM1, result.getHomeTeam());
        assertEquals(AWAY_TEAM1, result.getAwayTeam());
        assertTrue(result.isActive());
        assertNotNull(result.getStartDateTime());
    }

    @Test
    public void shouldUpdateScoreForProperGame() {
        service.startGame(HOME_TEAM2, AWAY_TEAM2);
        Game result = service.updateScore(HOME_TEAM2, SCORE_1, AWAY_TEAM2, SCORE_0);
        assertNotNull(result);
        assertEquals(SCORE_1, result.getHomeScore());
        assertEquals(SCORE_0, result.getAwayScore());
        assertEquals(HOME_TEAM2, result.getHomeTeam());
        assertEquals(AWAY_TEAM2, result.getAwayTeam());
    }

    @Test
    public void shouldFinishStartedGameAndRemoveItFromScoreBoard() {
        service.startGame(HOME_TEAM3, AWAY_TEAM3);
        service.finishGame(HOME_TEAM3, AWAY_TEAM3);

        List<Game> list = service.getScoreBoard();
        assertFalse(list.stream().anyMatch(
                game -> HOME_TEAM3.equals(game.getHomeTeam()) && AWAY_TEAM3.equals(game.getAwayTeam())));
    }

    @Test
    public void shouldGetStartedStoredGames() {
        Game game1 = service.startGame(HOME_TEAM4, AWAY_TEAM4);
        Game game2 = service.startGame(HOME_TEAM5, AWAY_TEAM5);

        List<Game> list = service.getScoreBoard();
        assertNotNull(list);
        assertTrue(list.contains(game1));
        assertTrue(list.contains(game2));
    }

    @Test
    public void shouldScoreBoardBeOrderedByTotalScoreAndStartDate() throws InterruptedException {

        service.startGame(HOME_TEAM6, AWAY_TEAM6);
        service.updateScore(HOME_TEAM6, SCORE_1, AWAY_TEAM6, SCORE_1);
        sleep(10);
        service.startGame(HOME_TEAM7, AWAY_TEAM7);
        service.updateScore(HOME_TEAM7, SCORE_1, AWAY_TEAM7, SCORE_0);
        sleep(10);
        service.startGame(HOME_TEAM8, AWAY_TEAM8);
        service.updateScore(HOME_TEAM8, SCORE_1, AWAY_TEAM8, SCORE_1);

        List<Game> scoreBoard = service.getScoreBoard();
        assertFalse(scoreBoard.isEmpty());
        assertEquals(HOME_TEAM8.getName(), scoreBoard.get(0).getHomeTeam().getName());
        assertEquals(AWAY_TEAM8.getName(), scoreBoard.get(0).getAwayTeam().getName());
        assertEquals(2, scoreBoard.get(0).getHomeScore() + scoreBoard.get(0).getAwayScore());

        assertEquals(HOME_TEAM6.getName(), scoreBoard.get(1).getHomeTeam().getName());
        assertEquals(AWAY_TEAM6.getName(), scoreBoard.get(1).getAwayTeam().getName());
        assertEquals(2, scoreBoard.get(1).getHomeScore() + scoreBoard.get(1).getAwayScore());

        assertEquals(HOME_TEAM7.getName(), scoreBoard.get(2).getHomeTeam().getName());
        assertEquals(AWAY_TEAM7.getName(), scoreBoard.get(2).getAwayTeam().getName());
        assertEquals(1, scoreBoard.get(2).getHomeScore() + scoreBoard.get(2).getAwayScore());
    }
}
