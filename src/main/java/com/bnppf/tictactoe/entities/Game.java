package com.bnppf.tictactoe.entities;

import com.bnppf.tictactoe.dtos.GameDTO;
import com.bnppf.tictactoe.enumerations.GameStatus;
import com.bnppf.tictactoe.enumerations.Symbol;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity(name = "Game")
@Table(name = "game")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int turnCount;

    @OneToMany(cascade = {CascadeType.ALL})
    private List<Player> players;

    @ElementCollection
    private Symbol[][] dashboard;

    private GameStatus gameStatus;

    private Symbol lastMove;

    public Game(){}

    public Game(GameDTO gameDTO) {
        this.players = new ArrayList<>(Arrays.asList(new Player(gameDTO.getPlayer1(), Symbol.X), new Player(gameDTO.getPlayer2(), Symbol.O)));
        this.turnCount = 0;
        this.dashboard = new Symbol[][]{new Symbol[]{
                Symbol.EMPTY, Symbol.EMPTY, Symbol.EMPTY},
                new Symbol[]{Symbol.EMPTY, Symbol.EMPTY, Symbol.EMPTY},
                new Symbol[]{Symbol.EMPTY, Symbol.EMPTY, Symbol.EMPTY}
        };
        this.gameStatus = GameStatus.ONGOING;
        this.lastMove = Symbol.EMPTY;
    }

    public int getTurnCount() {
        return turnCount;
    }

    public void setTurnCount(int turnCount) {
        this.turnCount = turnCount;
    }

    public Symbol[][] getDashboard() {
        return dashboard;
    }

    public void setDashboard(Symbol[][] dashboard) {
        this.dashboard = dashboard;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public Symbol getLastMove() {
        return lastMove;
    }

    public void setLastMove(Symbol lastMove) {
        this.lastMove = lastMove;
    }
}
