import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Game implements Serializable {

    static final long serialVersionUID = 1L;

    private final Date startDateTime;

    private final Team homeTeam;

    private final Team awayTeam;

    private Short homeScore;

    private Short awayScore;

    private boolean active;

    public Game(Team homeTeam, Team awayTeam) {

        this.startDateTime = new Date();
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeScore = 0;
        this.awayScore = 0;
        this.active = true;
    }

    public Date getStartDateTime() {
        return startDateTime;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Short getHomeScore() {
        return homeScore;
    }

    public Short getAwayScore() {
        return awayScore;
    }

    public void setHomeScore(Short homeScore) {
        this.homeScore = homeScore;
    }

    public void setAwayScore(Short awayScore) {
        this.awayScore = awayScore;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return active == game.active && startDateTime.equals(game.startDateTime) && homeTeam.equals(game.homeTeam)
                && awayTeam.equals(game.awayTeam) && Objects.equals(homeScore, game.homeScore)
                && Objects.equals(awayScore, game.awayScore);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDateTime, homeTeam, awayTeam, homeScore, awayScore, active);
    }

    @Override
    public String toString() {
        return "Game{" +
                "startDateTime=" + startDateTime +
                ", homeTeam=" + homeTeam +
                ", homeScore=" + homeScore +
                ", awayTeam=" + awayTeam +
                ", awayScore=" + awayScore +
                ", active=" + active +
                '}';
    }
}
