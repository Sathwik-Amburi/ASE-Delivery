import { createSlice } from "@reduxjs/toolkit";

const dispatcher = {
  id: "63bd2d47dea40908ea916896",
  email: "babushka@gmail.ru",
  pass: "p@ss",
  role: "Dispatcher",
};

const initialState = {
  dispatcherList: [dispatcher],
  isLoading: true,
};

const dispatcherSlice = createSlice({
  name: "dispatchers",
  initialState,
  reducers: {
    editDispatcher: (state, action) => {
      const { id, email, pass } = action.payload;
      const dispatcher = state.dispatcherList.find(
        (dispatcher) => dispatcher.id === id
      );
      if (dispatcher) {
        dispatcher.email = email;
        dispatcher.pass = pass;
      }
    },
    addDispatcher: (state, action) => {
      const formData = action.payload;
      state.dispatcherList.push(formData);
    },
    deleteDispatcher: (state, action) => {
      const id = action.payload;
      state.dispatcherList = state.dispatcherList.filter(
        (dispatcher) => dispatcher.id !== id
      );
    },
  },
});

export default dispatcherSlice.reducer;
export const { editDispatcher, addDispatcher, deleteDispatcher } =
  dispatcherSlice.actions;
