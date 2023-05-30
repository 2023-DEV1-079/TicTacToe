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

## Dependencies
You will need Java 17 to build the project

## Usage
There are 2 possibilities, building only the API or building
the API with the frontend embedded.

### Building the jar
To build only the API we need to execute:
> ./mvnw clean install

To build the API and the frontend:
> ./mvnw clean install -P frontend


### Starting the application
And then to execute the application:

> java -jar target/tictactoe-0.0.1-SNAPSHOT.jar

This will start both the Frontend app and the SpringBoot API

We will have the application running in the url:
http://localhost:8080


## API
Here is the documentation regarding all the API calls that can be done:
https://documenter.getpostman.com/view/7003898/2s93m4Y3Nc
https://documenter.getpostman.com/view/7003898/2s93m61MPN


## Frontend
The front end is a react application created with Vite.
It is already embedded inside the JAR file, so once runned the application
the frontend is accessible in localhost:8080

The technology used to create the frontend application are:
-  Vite 4.3.2 
-  Node 18.16.0
-  Npm 9.6.7

All of them were the latest version when the application was created and there is no need to have them installed as they will be automatically
compiled in the JAR file.
