import { useContext, useRef, useState } from "react";
import useGame from "./useGame";
import { ModalContext } from "../contexts/ModalContext";
import { createGame } from "../services/gameService";
import { TURNS } from "../constants/constants";

function useCreateGame() {
  const formRef = useRef();
  const [missingPlayers, setMissingPlayers] = useState(false);
  const { setGame, setActiveTurn } = useGame();
  const { createGameModal, setCreateGameModal } = useContext(ModalContext);

  const handleSubmit = (event) => {
    event.preventDefault();

    const form = new FormData(formRef.current);
    if (form.get("player1") === "" || form.get("player2") === "") {
      setMissingPlayers(true);
    } else {
      setMissingPlayers(false);

      createGame(formRef.current)
        .then((res) => {
          setGame(res.data);
          setCreateGameModal(!createGameModal);
          setActiveTurn(TURNS.X);
        })
        .catch((err) => console.log(err));
    }
  };
  return { handleSubmit, missingPlayers, formRef, createGameModal };
}

export default useCreateGame;
