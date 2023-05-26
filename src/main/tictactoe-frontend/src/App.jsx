import Cell from "./components/Cell";
import CreateGameModal from "./components/CreateGameModal";
import useGame from "./hooks/useGame";
import { TURNS } from "./constants/constants";
import EndGameModal from "./components/endGameModal";

function App() {
  const { game, updateBoard, activeTurn } = useGame();

  return (
    <main className="h-screen flex flex-col items-center justify-center z-20 relative">
      <CreateGameModal />
      <EndGameModal gameStatus={game?.gameStatus}></EndGameModal>.
      <h1 className="text-5xl md:text-7xl font-bold">Tic Tac Toe</h1>
      <section className="grid grid-cols-3 gap-4 py-4">
        {game
          ? game?.dashboard.map((nested, row) => {
              return nested.map((_, col) => {
                return (
                  <Cell
                    key={row + col}
                    row={row}
                    col={col}
                    updateCell={updateBoard}
                  >
                    {game?.dashboard[row][col] === "EMPTY"
                      ? ""
                      : game?.dashboard[row][col]}
                  </Cell>
                );
              });
            })
          : Array(9)
              .fill(null)
              .map((_, i) => <Cell key={i}></Cell>)}
      </section>
      <section className="flex flex-col items-center space-y-4 text-5xl font-bold">
        <h2 className="text-3xl md:text-5xl font-bold ">Turns</h2>
        <div className="flex space-x-4 text-3xl">
          <div className="flex flex-col items-center space-y-4">
            <Cell updateCell={() => {}} isSelected={activeTurn == TURNS.X}>
              {TURNS.X}
            </Cell>
            <strong>{game?.players[0].name}</strong>
          </div>
          <div className="flex flex-col items-center space-y-4 ">
            <Cell updateCell={() => {}} isSelected={activeTurn == TURNS.O}>
              {TURNS.O}
            </Cell>
            <strong>{game?.players[1].name}</strong>
          </div>
        </div>
      </section>
    </main>
  );
}

export default App;
