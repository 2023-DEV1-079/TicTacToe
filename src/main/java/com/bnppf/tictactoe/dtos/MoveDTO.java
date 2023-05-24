package com.bnppf.tictactoe.dtos;

import com.bnppf.tictactoe.enumerations.Symbol;

public class MoveDTO {

    private Symbol symbol;
    private int row;

    private int column;

    private Long gameId;

    public MoveDTO(Symbol symbol, int row, int column, Long gameId) {
        this.symbol = symbol;
        this.row = row;
        this.column = column;
        this.gameId = gameId;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }
}
