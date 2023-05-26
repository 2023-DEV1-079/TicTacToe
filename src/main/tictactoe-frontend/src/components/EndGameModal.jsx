import { useContext } from "react";
import { ModalContext } from "../contexts/ModalContext";
import StyledButton from "./SyledButton";

const EndGameModal = ({ gameStatus }) => {
  const { endOfGameModal, setEndOfGameModal, setCreateGameModal } =
    useContext(ModalContext);

  const gameStatusMessage = {
    XWIN: "X wins!",
    OWIN: "O WINS!",
    DRAW: "It's a draw!",
  };

  const handleClick = () => {
    setEndOfGameModal(false);
    setCreateGameModal(true);
  };

  return (
    <div
      className={`absolute h-screen w-full top-0 bg-gray-600/50 grid place-items-center ${
        endOfGameModal ? "z-30 opacity-100" : "-z-10 opacity-0"
      }`}
    >
      <div
        className={`h-1/2 w-1/2 rounded-xl absolute bg-white text-3xl flex flex-col items-center justify-center`}
      >
        <h1>{gameStatusMessage[gameStatus]}</h1>
        <StyledButton onClick={handleClick}>{"Create game"}</StyledButton>
      </div>
    </div>
  );
};

export default EndGameModal;
