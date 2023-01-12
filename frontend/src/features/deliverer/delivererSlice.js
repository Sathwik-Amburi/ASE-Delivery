import { createSlice } from "@reduxjs/toolkit";

const deliverer = {
  id: "63bd2d47dea40908ea916896",
  email: "babushka@gmail.ru",
  pass: "p@ss",
  role: "Deliverer",
};

const initialState = {
  delivererList: [deliverer],
  isLoading: true,
};

const delivererSlice = createSlice({
  name: "deliverers",
  initialState,
  reducers: {
    editDeliverer: (state, action) => {
      const { id, email, pass } = action.payload;
      const deliverer = state.delivererList.find(
        (deliverer) => deliverer.id === id
      );
      if (deliverer) {
        deliverer.email = email;
        deliverer.pass = pass;
      }
    },
    addDeliverer: (state, action) => {
      const formData = action.payload;
      state.delivererList.push(formData);
    },
    deleteDeliverer: (state, action) => {
      const id = action.payload;
      state.delivererList = state.delivererList.filter(
        (deliverer) => deliverer.id !== id
      );
    },
  },
});

export default delivererSlice.reducer;
export const { editDeliverer, addDeliverer, deleteDeliverer } =
  delivererSlice.actions;
