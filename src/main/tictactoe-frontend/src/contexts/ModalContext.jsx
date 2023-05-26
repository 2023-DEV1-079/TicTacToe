import { createContext, useState } from "react";

export const ModalContext = createContext(null);

export function ModalProvider({ children }) {
  const [endOfGameModal, setEndOfGameModal] = useState(false);
  const [createGameModal, setCreateGameModal] = useState(true);

  return (
    <ModalContext.Provider
      value={{
        endOfGameModal,
        setEndOfGameModal,
        createGameModal,
        setCreateGameModal,
      }}
    >
      {children}
    </ModalContext.Provider>
  );
}
