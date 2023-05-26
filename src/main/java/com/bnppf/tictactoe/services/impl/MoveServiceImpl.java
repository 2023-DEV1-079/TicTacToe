package com.bnppf.tictactoe.services.impl;

import com.bnppf.tictactoe.dtos.MoveDTO;
import com.bnppf.tictactoe.entities.Game;
import com.bnppf.tictactoe.enumerations.GameStatus;
import com.bnppf.tictactoe.enumerations.Symbol;
import com.bnppf.tictactoe.exceptions.DatabaseException;
import com.bnppf.tictactoe.exceptions.InvalidMoveException;
import com.bnppf.tictactoe.services.GameService;
import com.bnppf.tictactoe.services.MoveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class MoveServiceImpl implements MoveService {

    private static final int FIRST_POSSIBLE_WINNING_MOVE = 5;
    private static final  int LAST_MOVE = 9;

    @Autowired
    private GameService gameService;

    /**
     * Method used to make a move in the dashboard
     * @param moveDTO The DTO containing the information regarding the move
     * @return The game where the move was done
     * @throws InvalidMoveException Exception thrown if the movement breaks any rules
     * @throws DatabaseException Exception thrown if there is any issue when saving the updated game
     */
    @Override
    public Game makeAMove(MoveDTO moveDTO) throws InvalidMoveException, DatabaseException, NoSuchElementException {
        Game game = gameService.getGame(moveDTO.getGameId());

        //Validate the moves and throw exception if something ilegal happens.
        validateMove(game, moveDTO);

        //Increment turn count
        updateGameEntity(game, moveDTO);

        //Update the game in the DB
        return gameService.updateGame(game);

    }

    /**
     * Method to update the information in the game entity
     * @param game  The game entitity to be updated
     * @param moveDTO The move DTO containing information regarding the move done
     */
    private void updateGameEntity(Game game, MoveDTO moveDTO){
        game.setTurnCount(game.getTurnCount()+1);
        game.setLastMove(moveDTO.getSymbol());

        game.getDashboard()[moveDTO.getRow()][moveDTO.getColumn()] = moveDTO.getSymbol();

        boolean winner = false;

        if(game.getTurnCount() >= FIRST_POSSIBLE_WINNING_MOVE) {
            winner = checkWinner(game.getDashboard(), moveDTO.getSymbol(), moveDTO.getRow(), moveDTO.getColumn());
        }

        if(winner) {
            game.setGameStatus(moveDTO.getSymbol() == Symbol.X ? GameStatus.XWIN : GameStatus.OWIN);
        } else if(game.getTurnCount() == LAST_MOVE) {
            game.setGameStatus(GameStatus.DRAW);
        }

    }

    /**
     * Validates that the move is legal and there is not issues associated to it
     * @param game The Game entity
     * @param moveDTO The moveDTO that contains the information of the move that wants to be done
     * @throws InvalidMoveException Exception that is thrown if there is any rule broken
     */
    private void validateMove(Game game, MoveDTO moveDTO) throws InvalidMoveException {
        String message = null;

        if(game.getGameStatus() != GameStatus.ONGOING) {
            message = "This game has already ended, please start a new one";
        } else if(game.getLastMove() == moveDTO.getSymbol()){
            message = String.format("The user %s was the last to move",moveDTO.getSymbol().toString());
        } else if(game.getDashboard()[moveDTO.getRow()][moveDTO.getColumn()] != Symbol.EMPTY) {
            message = "The position is not empty";
        } else if(game.getTurnCount() == 0 && moveDTO.getSymbol() != Symbol.X){
            message = "X always have to play first";
        }

        if (message != null){
            throw new InvalidMoveException(message);
        }

    }

    /**
     *
     * @param dashboard The dashboard to check if there is a winner
     * @param symbol The symbol that did the last move
     * @param row The row of the last move
     * @param column The column of the last move
     * @return True if there is a winner, false otherwise
     */
    private boolean checkWinner(Symbol[][] dashboard, Symbol symbol, int row, int column){
        return (checkRow(dashboard, row,symbol)) || (checkColumn(dashboard, column, symbol))
                || (column == row && checkDiagonal(dashboard,symbol))
                || (column + row == dashboard.length - 1 && checkAntiDiagonal(dashboard,symbol));
    }


    /**
     * Checks if there is a winner in the row of the last move
     * @param dashboard The dashboard to check if there is a winner
     * @param row The row of the last move
     * @param symbol The symbol that did the last move
     * @return true if there is a winner, false otherwise
     */
    private boolean checkRow(Symbol[][] dashboard,int row, Symbol symbol){
        for (int i = 0; i < dashboard.length; ++i){
            if(dashboard[row][i] != symbol){
                break;
            }

            if(i == dashboard.length - 1)
                return true;
        }
        return false;
    }

    /**
     * Checks if there is a winner in the column of the last move
     * @param dashboard The dashboard to check if there is a winner
     * @param column The column of the last move
     * @param symbol The symbol that did the last move
     * @return true if there is a winner, false otherwise
     */
    private boolean checkColumn(Symbol[][] dashboard,int column, Symbol symbol){
        for (int i = 0; i < dashboard.length; ++i){
            if(dashboard[i][column] != symbol){
                break;
            }

            if(i == dashboard.length - 1)
                return true;
        }
        return false;
    }

    /**
     * Checks if there is a winner in the diagonal of the last move
     * @param dashboard The dashboard to check if there is a winner
     * @param symbol The symbol that did the last move
     * @return true if there is a winner, false otherwise
     */
    private boolean checkDiagonal(Symbol[][] dashboard, Symbol symbol){
        for(int i = 0; i < dashboard.length; i++){
            if(dashboard[i][i] != symbol)
                break;
            if(i == dashboard.length-1){
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if there is a winner in the antidiagonal of the last move
     * @param dashboard The dashboard to check if there is a winner
     * @param symbol The symbol that did the last move
     * @return true if there is a winner, false otherwise
     */
    private boolean checkAntiDiagonal(Symbol[][] dashboard, Symbol symbol){
        for(int i = 0; i < dashboard.length; i++){
            if(dashboard[i][(dashboard.length-1)-i] != symbol)
                break;
            if(i == dashboard.length-1){
                return true;
            }
        }
        return false;
    }
}
