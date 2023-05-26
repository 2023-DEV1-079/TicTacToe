const Cell = ({ children, updateCell, row, col, isSelected }) => {
  const handleClick = () => {
    if (children === "") updateCell(row, col);
  };

  return (
    <div
      onClick={handleClick}
      className={` h-24 w-24 rounded-lg border-4 grid place-items-center text-5xl ${
        isSelected
          ? "text-white bg-[#FFA62B]  border-[#FFA62B]"
          : "border-gray-800"
      }
    }`}
    >
      {children}
    </div>
  );
};

export default Cell;
