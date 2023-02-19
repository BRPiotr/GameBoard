import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static java.lang.Thread.sleep;
import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;

public class GameServiceTest {

    private static final Short SCORE_0 = 0;
    private static final Short SCORE_1 = 1;
    private static final Short SCORE_2 = 2;

    private static final Team HOME_TEAM1 = new Team("homeTeam1");
    private static final Team HOME_TEAM2 = new Team("homeTeam2");
    private static final Team HOME_TEAM3 = new Team("homeTeam3");
    private static final Team HOME_TEAM4 = new Team("homeTeam4");
    private static final Team HOME_TEAM5 = new Team("homeTeam5");
    private static final Team HOME_TEAM6 = new Team("homeTeam6");
    private static final Team HOME_TEAM7 = new Team("homeTeam7");
    private static final Team HOME_TEAM8 = new Team("homeTeam8");
    private static final Team HOME_TEAM9 = new Team("homeTeam9");

    private static final Team AWAY_TEAM1 = new Team("awayTeam1");
    private static final Team AWAY_TEAM2 = new Team("awayTeam2");
    private static final Team AWAY_TEAM3 = new Team("awayTeam3");
    private static final Team AWAY_TEAM4 = new Team("awayTeam4");
    private static final Team AWAY_TEAM5 = new Team("awayTeam5");
    private static final Team AWAY_TEAM6 = new Team("awayTeam6");
    private static final Team AWAY_TEAM7 = new Team("awayTeam7");
    private static final Team AWAY_TEAM8 = new Team("awayTeam8");
    private static final Team AWAY_TEAM9 = new Team("awayTeam9");

    private GameService service;

    @Before
    public void setUp() {
        service = new GameService();
    }

    @Test
    public void shouldStartGameAndInitialiseWithScoreStartDateAndActive() {
        service.startGame(HOME_TEAM1, AWAY_TEAM1);

        List<Game> board = service.getScoreBoard();

        assertEquals(1, board.size());
        Game result = board.get(0);
        assertNotNull(result);
        assertEquals(HOME_TEAM1, result.getHomeTeam());
        assertEquals(AWAY_TEAM1, result.getAwayTeam());
        assertTrue(result.isActive());
        assertNotNull(result.getStartDateTime());

        service.finishGame(HOME_TEAM1, AWAY_TEAM1);
    }

    @Test
    public void shouldUpdateScoreForProperGame() {
        service.startGame(HOME_TEAM2, AWAY_TEAM2);
        service.updateScore(HOME_TEAM2, SCORE_1, AWAY_TEAM2, SCORE_0);

        List<Game> board = service.getScoreBoard();
        assertEquals(1, board.size());
        Game result = board.get(0);

        assertNotNull(result);
        assertEquals(SCORE_1, result.getHomeScore());
        assertEquals(SCORE_0, result.getAwayScore());
        assertEquals(HOME_TEAM2, result.getHomeTeam());
        assertEquals(AWAY_TEAM2, result.getAwayTeam());

        service.finishGame(HOME_TEAM2, AWAY_TEAM2);
    }

    @Test
    public void shouldShowRecentUpdatedScoreForProperGame() {
        service.startGame(HOME_TEAM2, AWAY_TEAM2);
        service.updateScore(HOME_TEAM2, SCORE_1, AWAY_TEAM2, SCORE_0);
        service.updateScore(HOME_TEAM2, SCORE_0, AWAY_TEAM2, SCORE_1);
        service.updateScore(HOME_TEAM2, SCORE_0, AWAY_TEAM2, SCORE_2);

        List<Game> board = service.getScoreBoard();
        assertEquals(1, board.size());
        Game result = board.get(0);

        assertNotNull(result);
        assertEquals(SCORE_0, result.getHomeScore());
        assertEquals(SCORE_2, result.getAwayScore());
        assertEquals(HOME_TEAM2, result.getHomeTeam());
        assertEquals(AWAY_TEAM2, result.getAwayTeam());

        service.finishGame(HOME_TEAM2, AWAY_TEAM2);
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
        Game game0 = service.startGame(HOME_TEAM9, AWAY_TEAM9);
        service.finishGame(HOME_TEAM9, AWAY_TEAM9);
        Game game1 = service.startGame(HOME_TEAM4, AWAY_TEAM4);
        Game game2 = service.startGame(HOME_TEAM5, AWAY_TEAM5);

        List<Game> list = service.getScoreBoard();
        assertNotNull(list);
        assertTrue(list.contains(game1));
        assertTrue(list.contains(game2));
        assertFalse(list.contains(game0));

        service.finishGame(HOME_TEAM4, AWAY_TEAM4);
        service.finishGame(HOME_TEAM5, AWAY_TEAM5);
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

        service.startGame(HOME_TEAM3, AWAY_TEAM3);
        service.updateScore(HOME_TEAM3, SCORE_2, AWAY_TEAM3, SCORE_2);
        service.finishGame(HOME_TEAM3, AWAY_TEAM3);

        List<Game> scoreBoard = service.getScoreBoard();
        assertEquals(3, scoreBoard.size());
        assertEquals(HOME_TEAM8.getName(), scoreBoard.get(0).getHomeTeam().getName());
        assertEquals(AWAY_TEAM8.getName(), scoreBoard.get(0).getAwayTeam().getName());
        assertEquals(2, scoreBoard.get(0).getHomeScore() + scoreBoard.get(0).getAwayScore());

        assertEquals(HOME_TEAM6.getName(), scoreBoard.get(1).getHomeTeam().getName());
        assertEquals(AWAY_TEAM6.getName(), scoreBoard.get(1).getAwayTeam().getName());
        assertEquals(2, scoreBoard.get(1).getHomeScore() + scoreBoard.get(1).getAwayScore());

        assertEquals(HOME_TEAM7.getName(), scoreBoard.get(2).getHomeTeam().getName());
        assertEquals(AWAY_TEAM7.getName(), scoreBoard.get(2).getAwayTeam().getName());
        assertEquals(1, scoreBoard.get(2).getHomeScore() + scoreBoard.get(2).getAwayScore());

        service.finishGame(HOME_TEAM6, AWAY_TEAM6);
        service.finishGame(HOME_TEAM7, AWAY_TEAM7);
        service.finishGame(HOME_TEAM8, AWAY_TEAM8);
    }

    @Test
    public void shouldNotStartGameTwice() throws InterruptedException {
        Game game1 = service.startGame(HOME_TEAM9, AWAY_TEAM9);
        sleep(10);
        Game game2 = service.startGame(HOME_TEAM9, AWAY_TEAM9);

        List<Game> scoreBoard = service.getScoreBoard();
        assertEquals(1, scoreBoard.size());
        assertTrue(scoreBoard.contains(game1));
        assertFalse(scoreBoard.contains(game2));

        service.finishGame(HOME_TEAM9, AWAY_TEAM9);
    }

    @Test
    public void shouldNotUpdateScoreForFinishedGame() {
        Game game = service.startGame(HOME_TEAM9, AWAY_TEAM9);
        assertSame(SCORE_0, game.getHomeScore());
        assertSame(SCORE_0, game.getAwayScore());
        service.finishGame(HOME_TEAM9, AWAY_TEAM9);

        service.updateScore(HOME_TEAM9, SCORE_1, AWAY_TEAM9, SCORE_1);
        List<Game> scoreBoard = service.getScoreBoard();
        assertTrue(scoreBoard.isEmpty());
    }

    @Test
    public void shouldGetScoreBoardFromExample() throws InterruptedException {
        //given
        Team mexico = new Team("Mexico");
        Team canada = new Team("Canada");
        Team spain = new Team("Spain");
        Team brazil = new Team("Brazil");
        Team germany = new Team("Germany");
        Team france = new Team("France");
        Team uruguay = new Team("Uruguay");
        Team italy = new Team("Italy");
        Team argentina = new Team("Argentina");
        Team australia = new Team("Australia");

        //when
        startGameAndUpdateScore(mexico, 0, canada, 5);
        startGameAndUpdateScore(spain, 10, brazil, 2);
        startGameAndUpdateScore(germany, 2, france, 2);
        startGameAndUpdateScore(uruguay, 6, italy, 6);
        startGameAndUpdateScore(argentina, 3, australia, 1);
        List<Game> result = service.getScoreBoard();

        //then
        assertSame(5, result.size());
        assertGameIsSame(result.get(0), uruguay, 6, italy, 6);
        assertGameIsSame(result.get(1), spain, 10, brazil, 2);
        assertGameIsSame(result.get(2), mexico, 0, canada, 5);
        assertGameIsSame(result.get(3), argentina, 3, australia, 1);
        assertGameIsSame(result.get(4), germany, 2, france, 2);

        service.finishGame(uruguay, italy);
        service.finishGame(spain, brazil);
        service.finishGame(mexico, canada);
        service.finishGame(argentina, australia);
        service.finishGame(germany, france);
    }

    private void assertGameIsSame(Game game, Team team1, int score1, Team team2, int score2) {
        assertSame(team1, game.getHomeTeam());
        assertSame(team2, game.getAwayTeam());
        assertSame((short) score1, game.getHomeScore());
        assertSame((short) score2, game.getAwayScore());
    }

    private void startGameAndUpdateScore(Team team1, int score1, Team team2, int score2) throws InterruptedException {
        service.startGame(team1, team2);
        service.updateScore(team1, (short) score1, team2, (short) score2);
        sleep(5);
    }


    @Test
    public void shouldNotUpdateScoreToNegativeScore() throws InterruptedException {
        startGameAndUpdateScore(HOME_TEAM1, -1, HOME_TEAM2, 2);
        List<Game> scoreBoard = service.getScoreBoard();
        assertSame(1, scoreBoard.size());
        assertGameIsSame(scoreBoard.get(0), HOME_TEAM1, 0, HOME_TEAM2, 0);

        service.updateScore(HOME_TEAM1, SCORE_0, HOME_TEAM2, (short) -2);
        assertSame(1, scoreBoard.size());
        assertGameIsSame(scoreBoard.get(0), HOME_TEAM1, 0, HOME_TEAM2, 0);

        service.finishGame(HOME_TEAM1, HOME_TEAM2);
    }
}
