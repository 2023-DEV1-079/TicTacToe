const StyledButton = ({ children, onClick }) => {
  return (
    <button
      onClick={onClick}
      className="rounded-md px-4 py-2 bg-[#FFA62B] text-white"
    >
      {children}
    </button>
  );
};

export default StyledButton;
