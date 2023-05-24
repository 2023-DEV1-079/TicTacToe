package com.bnppf.tictactoe.services;

import com.bnppf.tictactoe.dtos.GameDTO;
import com.bnppf.tictactoe.entities.Game;
import com.bnppf.tictactoe.exceptions.DatabaseException;

import java.util.List;

public interface GameService {
    Game createGame(GameDTO gameDTO) throws DatabaseException;

    List<Game> getAllGames() throws DatabaseException;

    Game updateGame(Game game) throws DatabaseException;

    Game getGame(Long id);
}
