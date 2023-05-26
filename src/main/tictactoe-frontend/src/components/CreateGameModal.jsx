import StyledButton from "./SyledButton";
import useCreateGame from "../hooks/useCreateGame";

const CreateGameModal = () => {
  const { handleSubmit, missingPlayers, formRef, createGameModal } =
    useCreateGame();

  return (
    <div
      className={`absolute h-screen w-full top-0 bg-gray-600/50 grid place-items-center ${
        createGameModal ? "z-30" : "-z-10 opacity-0"
      }`}
    >
      <div
        className={`md:h-1/2 md:w-1/2 h-1/2 w-3/4  rounded-xl absolute bg-white text-3xl`}
      >
        <form
          ref={formRef}
          className=" relative flex flex-col items-center justify-center h-full space-y-8 shadow-sm"
          onSubmit={handleSubmit}
        >
          <div className="flex flex-col space-y-4 lg:flex-row lg:space-x-2 lg:space-y-0 text-center">
            <div className="flex flex-col items-center">
              <label className="font-semibold" htmlFor="player1">
                Player 1
              </label>
              <input
                className="w-2/3 border-4 border-[#FFA62B] rounded-md px-1"
                name="player1"
                placeholder="Marcos"
              ></input>
            </div>

            <div className="flex flex-col items-center">
              <label className="font-semibold" htmlFor="player1">
                Player 2
              </label>
              <input
                className="w-2/3 border-4 border-[#FFA62B] rounded-md px-1"
                name="player2"
                placeholder="Alexandra"
              ></input>
            </div>
          </div>

          <StyledButton>{"Create game"}</StyledButton>
          {missingPlayers && (
            <div className="absolute top-10 w-4/5 bg-red-600 rounded-2xl p-8">
              <h2 className="text-2xl text-white">Please fill both players</h2>
            </div>
          )}
        </form>
      </div>
    </div>
  );
};

export default CreateGameModal;
