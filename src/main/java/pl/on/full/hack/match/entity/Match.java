package pl.on.full.hack.match.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import pl.on.full.hack.auth.entity.RankrUser;
import pl.on.full.hack.db.Venue;
import pl.on.full.hack.league.dto.MatchDTO;
import pl.on.full.hack.league.entity.League;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "matches")
@Data
@EqualsAndHashCode(exclude={"players"})
@ToString(exclude={"players"})
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "venue_id")
    private Venue venue;

    @OneToOne()
    @JoinColumn(name = "winner_id")
    private RankrUser winner;

    private String discipline;

    @ManyToOne()
    @JoinColumn(name = "league_id")
    private League league;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "match_player",
            joinColumns = @JoinColumn(name = "match_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<RankrUser> players = new HashSet<>();

    public MatchDTO getMatchDTO() {
        final MatchDTO matchDTO = new MatchDTO();
        matchDTO.setDiscipline(league.getDiscipline());
        matchDTO.setVenue(venue.getName());
        matchDTO.setWinner(winner.getUsername());
        return matchDTO;
    }
}
