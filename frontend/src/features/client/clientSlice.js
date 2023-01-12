import { createSlice } from "@reduxjs/toolkit";

const client = {
  id: "63bd2d47dea40908ea916896",
  email: "babushka@gmail.ru",
  pass: "p@ss",
  role: "Client",
};

const initialState = {
  clientList: [client],
  isLoading: true,
};

const clientSlice = createSlice({
  name: "clients",
  initialState,
  reducers: {
    editClient: (state, action) => {
      const { id, email, pass } = action.payload;
      const client = state.clientList.find((client) => client.id === id);
      if (client) {
        client.email = email;
        client.pass = pass;
      }
    },
    addClient: (state, action) => {
      const formData = action.payload;
      state.clientList.push(formData);
    },
    deleteClient: (state, action) => {
      const id = action.payload;
      state.clientList = state.clientList.filter((client) => client.id !== id);
    },
  },
});

export default clientSlice.reducer;
export const { editClient, addClient, deleteClient } = clientSlice.actions;
