package com.bnppf.tictactoe.controllers;

import com.bnppf.tictactoe.dtos.GameDTO;
import com.bnppf.tictactoe.entities.Game;
import com.bnppf.tictactoe.services.GameService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GameController.class)
class GameControllerTest {

    @MockBean
    private GameService gameService;

    @Autowired
    private MockMvc mvc;

    private static final GameDTO GAME_DTO  = new GameDTO("Marcos", "Alexandra");
    private static final GameDTO GAME_DTO_2  = new GameDTO("Carlos", "Ana");

    private static final String API_ENDPOINT = "/api/v1/game";

    private static final List<Game> GAMES = new ArrayList<>(Arrays.asList(new Game(GAME_DTO),new Game(GAME_DTO_2)));


    @Test
    void createGameShouldBeOkTest() throws Exception {
        Mockito.when(gameService.createGame(Mockito.any())).thenReturn(new Game(GAME_DTO));
        mvc.perform( MockMvcRequestBuilders
                        .post(API_ENDPOINT)
                        .content(asJsonString(GAME_DTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void createGameShouldReturnOngoingGameStatusTest() throws Exception {
        Mockito.when(gameService.createGame(Mockito.any())).thenReturn(new Game(GAME_DTO));
        mvc.perform( MockMvcRequestBuilders
                        .post(API_ENDPOINT)
                        .content(asJsonString(GAME_DTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.gameStatus").value("ONGOING"));
    }

    @Test
    void getAllGamesShouldReturnTwoGamesTest() throws Exception {
        Mockito.when(gameService.getAllGames()).thenReturn(GAMES);
        mvc.perform( MockMvcRequestBuilders
                        .get(API_ENDPOINT))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void getAllGamesShouldReturnEmptyTest() throws Exception {
        Mockito.when(gameService.getAllGames()).thenReturn(new ArrayList<>());
        mvc.perform( MockMvcRequestBuilders
                        .get(API_ENDPOINT))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0))
                .andDo(MockMvcResultHandlers.print());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}