import { useContext } from "react";
import { GameContext } from "../contexts/GameContext";
import { ModalContext } from "../contexts/ModalContext";
import { TURNS } from "../constants/constants";
import { makeAMove } from "../services/moveService";

export default function useGame() {
  const { game, setGame, activeTurn, setActiveTurn } = useContext(GameContext);

  const { setEndOfGameModal } = useContext(ModalContext);

  const updateBoard = (row, col) => {
    const data = { symbol: activeTurn, row: row, column: col, gameId: game.id };

    makeAMove(data)
      .then((res) => {
        setGame(res.data);
        if (res.data.gameStatus !== "ONGOING") {
          setEndOfGameModal(true);
        }
        setActiveTurn(activeTurn === TURNS.X ? TURNS.O : TURNS.X);
      })
      .catch((err) => {
        console.log(err);
      });
  };

  return { game, updateBoard, setGame, activeTurn, setActiveTurn };
}
