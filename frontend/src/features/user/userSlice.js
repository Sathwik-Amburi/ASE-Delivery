import { createSlice } from "@reduxjs/toolkit";

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
      const { id, email, role } = action.payload;
      state.id = id;
      state.email = email;
      state.role = role;
      state.loggedIn = true;
    },
    restUser: (state) => {
      state.id = "";
      state.email = "Guest";
      state.role = "";
    },
  },
});

export default userSlice.reducer;
export const { updateUser, restUser } = userSlice.actions;
