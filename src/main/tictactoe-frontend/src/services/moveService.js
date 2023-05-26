import axios from "axios";

export function makeAMove(data) {
  return axios.post("http://localhost:8080/api/v1/move", data, {
    headers: {
      "Content-Type": "application/json",
    },
  });
}
