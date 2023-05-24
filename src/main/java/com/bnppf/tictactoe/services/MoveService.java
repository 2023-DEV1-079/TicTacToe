package com.bnppf.tictactoe.services;

import com.bnppf.tictactoe.dtos.MoveDTO;
import com.bnppf.tictactoe.entities.Game;
import com.bnppf.tictactoe.exceptions.DatabaseException;
import com.bnppf.tictactoe.exceptions.InvalidMoveException;

public interface MoveService {
    Game makeAMove(MoveDTO moveDTO) throws InvalidMoveException, DatabaseException;
}
