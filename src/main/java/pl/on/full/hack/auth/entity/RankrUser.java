package pl.on.full.hack.auth.entity;

import lombok.*;
import pl.on.full.hack.match.entity.Match;
import pl.on.full.hack.league.entity.League;
import pl.on.full.hack.league.entity.LeaguePlayer;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "rankr_users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude={"createdLeagues", "matches", "leaguePlayer"})
@ToString(exclude={"createdLeagues", "matches", "leaguePlayer"})
public class RankrUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String username;

    private String password;

    @OneToMany(mappedBy = "creator")
    private Set<League> createdLeagues = new HashSet<>();

    @ManyToMany(mappedBy = "players")
    private Set<Match> matches = new HashSet<>();

    @OneToMany(mappedBy = "player")
    private Set<LeaguePlayer> leaguePlayer = new HashSet<>();
}
