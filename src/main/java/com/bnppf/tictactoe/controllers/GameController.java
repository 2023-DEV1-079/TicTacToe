package com.bnppf.tictactoe.controllers;

import com.bnppf.tictactoe.dtos.GameDTO;
import com.bnppf.tictactoe.entities.Game;
import com.bnppf.tictactoe.exceptions.DatabaseException;
import com.bnppf.tictactoe.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/game")
@CrossOrigin(origins = "http://localhost:5173")
public class GameController {

    @Autowired
    GameService gameService;


    @PostMapping
    public ResponseEntity<Game> createGame(@RequestBody GameDTO gameDTO) {
        Game savedGame;

        try {
            savedGame = gameService.createGame(gameDTO);
        } catch (DatabaseException e) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok(savedGame);
    }

    @GetMapping
    public ResponseEntity<List<Game>> getAllGames() {
        List<Game> games;

        try {
           games = gameService.getAllGames();
        } catch (DatabaseException e) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok(games);
    }

}
