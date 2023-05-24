package com.bnppf.tictactoe.services.impl;

import com.bnppf.tictactoe.dtos.GameDTO;
import com.bnppf.tictactoe.entities.Game;
import com.bnppf.tictactoe.exceptions.DatabaseException;
import com.bnppf.tictactoe.repositories.GameRepository;
import com.bnppf.tictactoe.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    GameRepository gameRepository;

    public GameServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    /**
     * Initialization of the game with the two player names
     * @param gameDTO Data transfer object containing the two player names
     * @return The new created game
     * @throws DatabaseException Exception that fires if the database statement fails
     */
    @Override
    public Game createGame(GameDTO gameDTO) throws DatabaseException {

        Game createdGame;
        try {
            Game game = new Game(gameDTO);
            createdGame = gameRepository.save(game);
        } catch (Exception e){
            throw new DatabaseException("Could not create the game",e);
        }

        return createdGame;

    }

    /**
     * Gets all the games saved
     * @return All the games that are saved in the DB
     *
     * @throws DatabaseException Exteption thrown if something goes wrong retrieveng the games from the DB
     */
    @Override
    public List<Game> getAllGames() throws DatabaseException {
        List<Game> allGames;

        try {
           allGames  = gameRepository.findAll();
        } catch (Exception e){
            throw new DatabaseException("Could not retrieve the games",e);
        }

        return allGames;
    }

}
