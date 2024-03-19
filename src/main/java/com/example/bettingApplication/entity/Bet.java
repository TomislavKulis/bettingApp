package com.example.bettingApplication.entity;

import com.example.bettingApplication.enums.Outcomes;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
public class Bet {

    public Bet(UUID id, Match match, Outcomes outcome, BetSlip betSlip) {
        this.id = id;
        this.setMatch(match);
        this.outcome = outcome;
        this.setBetSlip(betSlip);
    }

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne
    private Match match;

    @Enumerated(EnumType.STRING)
    private Outcomes outcome;

    @ManyToOne
    private BetSlip betSlip;

    public void setMatch(Match match){
        this.match = match;
        match.getBets().add(this);
    }

    public void setBetSlip(BetSlip betSlip){
        this.betSlip = betSlip;
        betSlip.getBets().add(this);
    }
}
