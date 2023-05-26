import axios from "axios";

export function createGame(form) {
  return axios.post("http://localhost:8080/api/v1/game", form, {
    headers: {
      "Content-Type": "application/json",
    },
  });
}
