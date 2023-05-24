package com.bnppf.tictactoe.controllers;

import com.bnppf.tictactoe.dtos.GameDTO;
import com.bnppf.tictactoe.dtos.MoveDTO;
import com.bnppf.tictactoe.entities.Game;
import com.bnppf.tictactoe.enumerations.Symbol;
import com.bnppf.tictactoe.exceptions.DatabaseException;
import com.bnppf.tictactoe.exceptions.InvalidMoveException;
import com.bnppf.tictactoe.helpers.TestsHelper;
import com.bnppf.tictactoe.services.MoveService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MoveController.class)
class MoveControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private MoveService moveService;

    private static final GameDTO GAME_DTO  = new GameDTO("Marcos", "Alexandra");
    private static final String API_ENDPOINT = "/api/v1/move";

    @Test
    void moveAPositionShouldBeOkTest() throws Exception {
        Game game = new Game(GAME_DTO);
        game.getDashboard()[0][0] = Symbol.X;
        when(moveService.makeAMove(Mockito.any())).thenReturn(game);
        mvc.perform(MockMvcRequestBuilders
                        .post(API_ENDPOINT)
                        .content(TestsHelper.asJsonString(new MoveDTO(Symbol.X,0,0,1L)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.dashboard[0][0]").value("X"));
    }

    @Test
    void moveAPositionShouldBeInvalidTest() throws Exception {
        Game game = new Game(GAME_DTO);
        game.getDashboard()[0][0] = Symbol.X;
        when(moveService.makeAMove(Mockito.any())).thenThrow(new InvalidMoveException("The position is already taken"));
        mvc.perform(MockMvcRequestBuilders
                        .post(API_ENDPOINT)
                        .content(TestsHelper.asJsonString(new MoveDTO(Symbol.X,0,0,1L)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void moveAPositionShouldBeInternalErrorTest() throws Exception {
        Game game = new Game(GAME_DTO);
        game.getDashboard()[0][0] = Symbol.X;
        when(moveService.makeAMove(Mockito.any())).thenThrow(new DatabaseException("Problem updating the game"));
        mvc.perform(MockMvcRequestBuilders
                        .post(API_ENDPOINT)
                        .content(TestsHelper.asJsonString(new MoveDTO(Symbol.X,0,0,1L)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andDo(MockMvcResultHandlers.print());
    }


}