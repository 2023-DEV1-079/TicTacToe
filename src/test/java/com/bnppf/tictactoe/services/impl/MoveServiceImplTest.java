package com.bnppf.tictactoe.services.impl;

import com.bnppf.tictactoe.dtos.GameDTO;
import com.bnppf.tictactoe.dtos.MoveDTO;
import com.bnppf.tictactoe.entities.Game;
import com.bnppf.tictactoe.enumerations.GameStatus;
import com.bnppf.tictactoe.enumerations.Symbol;
import com.bnppf.tictactoe.exceptions.DatabaseException;
import com.bnppf.tictactoe.exceptions.InvalidMoveException;
import com.bnppf.tictactoe.services.GameService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.when;


@SpringBootTest
class MoveServiceImplTest {

    @InjectMocks
    private MoveServiceImpl moveService;

    @Mock
    private GameService gameService;

    private static final GameDTO GAME_DTO  = new GameDTO("Marcos", "Alexandra");
    private static final MoveDTO MOVE_DTO = new MoveDTO(Symbol.X,0,0,1L);
    private static final MoveDTO MOVE_DTO2 = new MoveDTO(Symbol.O,0,0,1L);
    private static final MoveDTO MOVE_DTO_WIN_X = new MoveDTO(Symbol.X,1,2,1L);
    private static final MoveDTO MOVE_DTO_DRAW = new MoveDTO(Symbol.X,2,0,1L);
    private static final MoveDTO MOVE_DTO_DIAG_WIN = new MoveDTO(Symbol.X,2,2,1L);
    private static final MoveDTO MOVE_DTO_ANTI_DIAG_WIN = new MoveDTO(Symbol.X,2,0,1L);

    private static final Symbol[][] DASHBORD_WIN_X = new Symbol[][]{new Symbol[]{
            Symbol.X, Symbol.O, Symbol.X},
            new Symbol[]{Symbol.O, Symbol.O, Symbol.EMPTY},
            new Symbol[]{Symbol.O, Symbol.X, Symbol.X}};

    private static final Symbol[][] DASHBORD_DRAW = new Symbol[][]{new Symbol[]{
            Symbol.X, Symbol.O, Symbol.X},
            new Symbol[]{Symbol.O, Symbol.O, Symbol.X},
            new Symbol[]{Symbol.EMPTY, Symbol.X, Symbol.O}};

    private static final Symbol[][] DASHBORD_DIAG_WIN = new Symbol[][]{new Symbol[]{
            Symbol.X, Symbol.O, Symbol.O},
            new Symbol[]{Symbol.EMPTY, Symbol.X, Symbol.EMPTY},
            new Symbol[]{Symbol.EMPTY, Symbol.EMPTY, Symbol.EMPTY}};

    private static final Symbol[][] DASHBORD_ANTIDIAG_WIN = new Symbol[][]{new Symbol[]{
            Symbol.EMPTY, Symbol.O, Symbol.X},
            new Symbol[]{Symbol.EMPTY, Symbol.X, Symbol.EMPTY},
            new Symbol[]{Symbol.EMPTY, Symbol.EMPTY, Symbol.O}};

    @Test
    void makeAMoveShouldWorkCorrectlyTest() throws DatabaseException, InvalidMoveException {
        Game game = new Game(GAME_DTO);
        game.setId(1L);

        Game updatedGame = new Game(GAME_DTO);
        updatedGame.getDashboard()[0][0] = Symbol.X;

        when(gameService.getGame(MOVE_DTO.getGameId())).thenReturn(game);
        when(gameService.updateGame(game)).thenReturn(updatedGame);
        Game updatedGameFromService = moveService.makeAMove(MOVE_DTO);
        Assertions.assertEquals(Symbol.X,updatedGameFromService.getDashboard()[0][0]);
    }

    @Test
    void makeAMoveWithConsecutivePlayerShouldThrowExceptionTest() throws DatabaseException {
        Game game = new Game(GAME_DTO);
        game.setId(1L);
        game.setLastMove(Symbol.X);

        Game updatedGame = new Game(GAME_DTO);
        updatedGame.getDashboard()[0][0] = Symbol.X;

        when(gameService.getGame(MOVE_DTO.getGameId())).thenReturn(game);
        when(gameService.updateGame(game)).thenReturn(updatedGame);

        InvalidMoveException thrown = Assertions.assertThrows(InvalidMoveException.class, () -> moveService.makeAMove(MOVE_DTO));
        Assertions.assertEquals("The user X was the last to move", thrown.getMessage());
    }

    @Test
    void makeAMoveOnAFilledPositionShouldThrowExceptionTest() {
        Game game = new Game(GAME_DTO);
        game.getDashboard()[0][0] = Symbol.X;
        game.setId(1L);


        when(gameService.getGame(MOVE_DTO.getGameId())).thenReturn(game);

        InvalidMoveException thrown = Assertions.assertThrows(InvalidMoveException.class, () -> moveService.makeAMove(MOVE_DTO));
        Assertions.assertEquals("The position is not empty", thrown.getMessage());
    }

    @Test
    void makeAMoveOnFinishedGameShouldThrowExceptionTest() {
        Game game = new Game(GAME_DTO);
        game.setId(1L);
        game.setGameStatus(GameStatus.OWIN);

        when(gameService.getGame(MOVE_DTO.getGameId())).thenReturn(game);

        InvalidMoveException thrown = Assertions.assertThrows(InvalidMoveException.class, () -> moveService.makeAMove(MOVE_DTO));
        Assertions.assertEquals("This game has already ended, please start a new one", thrown.getMessage());
    }

    @Test
    void makeAMoveOFirstShouldThrowExceptionTest() {
        Game game = new Game(GAME_DTO);
        game.setId(1L);

        when(gameService.getGame(MOVE_DTO.getGameId())).thenReturn(game);

        InvalidMoveException thrown = Assertions.assertThrows(InvalidMoveException.class, () -> moveService.makeAMove(MOVE_DTO2));
        Assertions.assertEquals("X always have to play first", thrown.getMessage());
    }


    @Test
    void makeAMoveMakesXWinShouldWorkTest() throws DatabaseException, InvalidMoveException {
        Game game = new Game(GAME_DTO);
        game.setId(1L);
        game.setTurnCount(8);
        game.setDashboard(DASHBORD_WIN_X);

        when(gameService.getGame(MOVE_DTO_WIN_X.getGameId())).thenReturn(game);
        when(gameService.updateGame(game)).thenReturn(game);

        Game updatedGame = moveService.makeAMove(MOVE_DTO_WIN_X);
        Assertions.assertEquals(GameStatus.XWIN,updatedGame.getGameStatus());
    }

    @Test
    void makeAMoveMakesDrawShouldWorkTest() throws DatabaseException, InvalidMoveException {
        Game game = new Game(GAME_DTO);
        game.setId(1L);
        game.setTurnCount(8);
        game.setDashboard(DASHBORD_DRAW);

        when(gameService.getGame(MOVE_DTO_DRAW.getGameId())).thenReturn(game);
        when(gameService.updateGame(game)).thenReturn(game);

        Game updatedGame = moveService.makeAMove(MOVE_DTO_DRAW);
        Assertions.assertEquals(GameStatus.DRAW,updatedGame.getGameStatus());
    }

    @Test
    void makeAMoveDiagonalWinShouldWorkTest() throws DatabaseException, InvalidMoveException {
        Game game = new Game(GAME_DTO);
        game.setId(1L);
        game.setTurnCount(8);
        game.setDashboard(DASHBORD_DIAG_WIN);

        when(gameService.getGame(MOVE_DTO_DIAG_WIN.getGameId())).thenReturn(game);
        when(gameService.updateGame(game)).thenReturn(game);

        Game updatedGame = moveService.makeAMove(MOVE_DTO_DIAG_WIN);
        Assertions.assertEquals(GameStatus.XWIN,updatedGame.getGameStatus());
    }

    @Test
    void makeAMoveAntiDiagonalWinShouldWorkTest() throws DatabaseException, InvalidMoveException {
        Game game = new Game(GAME_DTO);
        game.setId(1L);
        game.setTurnCount(8);
        game.setDashboard(DASHBORD_ANTIDIAG_WIN);

        when(gameService.getGame(MOVE_DTO_ANTI_DIAG_WIN.getGameId())).thenReturn(game);
        when(gameService.updateGame(game)).thenReturn(game);

        Game updatedGame = moveService.makeAMove(MOVE_DTO_ANTI_DIAG_WIN);
        Assertions.assertEquals(GameStatus.XWIN,updatedGame.getGameStatus());
    }

}