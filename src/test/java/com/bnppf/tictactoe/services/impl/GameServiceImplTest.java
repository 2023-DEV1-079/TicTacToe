package com.bnppf.tictactoe.services.impl;

import com.bnppf.tictactoe.dtos.GameDTO;
import com.bnppf.tictactoe.entities.Game;
import com.bnppf.tictactoe.enumerations.GameStatus;
import com.bnppf.tictactoe.exceptions.DatabaseException;
import com.bnppf.tictactoe.repositories.GameRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@SpringBootTest
class GameServiceImplTest {

    @InjectMocks
    GameServiceImpl gameService;

    @Mock
    GameRepository gameRepository;

    private static final GameDTO GAME_DTO  = new GameDTO("Marcos", "Alexandra");
    private static final GameDTO GAME_DTO_2  = new GameDTO("Carlos", "Ana");
    private static final List<Game> GAMES = new ArrayList<>(Arrays.asList(new Game(GAME_DTO),new Game(GAME_DTO_2)));



    @BeforeEach
    void setUp(){
        when(gameRepository.save(any())).thenReturn(new Game(GAME_DTO));
        when(gameRepository.findAll()).thenReturn(GAMES);
    }

    @Test
    void createGameShouldPassTest() throws DatabaseException {
        Game createdGame = gameService.createGame(GAME_DTO);
        Assertions.assertEquals(GameStatus.ONGOING,createdGame.getGameStatus());
        Assertions.assertEquals("Marcos",createdGame.getPlayers().get(0).getName());
    }

    @Test
    void createGameShouldThrowExceptionTest() {
        Assertions.assertThrows(DatabaseException.class, () -> gameService.createGame(null));
    }

    @Test
    void getAllGamesTest() throws DatabaseException {
        List<Game> allGames = gameService.getAllGames();
        Assertions.assertEquals(allGames.size(),GAMES.size());
    }




}