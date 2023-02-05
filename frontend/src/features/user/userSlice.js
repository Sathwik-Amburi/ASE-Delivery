import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import axios from "axios";

const initialState = {
  id: "",
  email: "Guest",
  role: "",
  isLoading: true,
  loggedIn: false,
};

const userSlice = createSlice({
  name: "user",
  initialState,
  reducers: {
    updateUser: (state, action) => {
      const { email, role } = action.payload;
      state.email = email;
      state.role = role;
      state.loggedIn = true;
    },
    resetUser: (state) => {
      state.id = "";
      state.email = "Guest";
      state.role = "";
      state.loggedIn = false;
    },
  },
});

export default userSlice.reducer;
export const { updateUser, resetUser } = userSlice.actions;
