import { createContext, useState } from "react";
import { TURNS } from "../constants/constants";

export const GameContext = createContext(null);

export function GameProvider({ children }) {
  const [game, setGame] = useState();
  const [activeTurn, setActiveTurn] = useState(TURNS.X);

  return (
    <GameContext.Provider
      value={{
        game,
        setGame,
        activeTurn,
        setActiveTurn,
      }}
    >
      {children}
    </GameContext.Provider>
  );
}
