import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameTest {
    static Short ZERO = 0;
    static Team TEAM1 = new Team("team1");
    static Team TEAM2 = new Team("team2");
        
    @Test
    public void shouldCreateGameWithZeroZeroScore() {
        Game result = new Game(TEAM1, TEAM2);
        assertEquals(ZERO, result.getHomeScore());
        assertEquals(ZERO, result.getAwayScore());
    }
    
    @Test
    public void shouldCreateGameActive(){
        Game result = new Game(TEAM1, TEAM2);
        assertTrue(result.isActive());
    }
    
    @Test
    public void shouldCreateGameStartedDateTime(){
        Game result = new Game(TEAM1, TEAM2);
        assertNotNull(result.getStartDateTime());
    }
    
    @Test
    public void shouldCreateGameWithTeamsAssigned(){
        Game result = new Game(TEAM1, TEAM2);
        assertEquals(TEAM1, result.getHomeTeam());
        assertEquals(TEAM2, result.getAwayTeam());
    }

}

