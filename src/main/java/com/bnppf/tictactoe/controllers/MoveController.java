package com.bnppf.tictactoe.controllers;

import com.bnppf.tictactoe.dtos.MoveDTO;
import com.bnppf.tictactoe.entities.Game;
import com.bnppf.tictactoe.exceptions.DatabaseException;
import com.bnppf.tictactoe.exceptions.InvalidMoveException;
import com.bnppf.tictactoe.services.MoveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/move")
public class MoveController {

    @Autowired
    MoveService moveService;

    @PostMapping
    public ResponseEntity move(@RequestBody MoveDTO moveDTO) {
        Game game;

        try {
            game = moveService.makeAMove(moveDTO);
        } catch (InvalidMoveException | NoSuchElementException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (DatabaseException e) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok(game);
    }





}
