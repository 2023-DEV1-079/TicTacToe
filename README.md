# TicTacToe
Coding Kata for BNPPF

## Rules

The rules are described below :

- X always goes first.
- Players cannot play on a played position.
- Players alternate placing X’s and O’s on the board until either:
    - One player has three in a row, horizontally, vertically or diagonally
    - All nine squares are filled.
- If a player is able to draw three X’s or three O’s in a row, that player wins.
- If all nine squares are filled and neither player has three in a row, the game is a draw.

## Usage

To start the project we first need to execute: 

> mvn clean install

And then to execute the application:

> mvn spring-boot:run


We will have the application running in the url:
http://localhost:8080


## API

Here is the documentation regarding all the API calls that can be done:
https://documenter.getpostman.com/view/7003898/2s93m4Y3Nc