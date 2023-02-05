import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import axios from "axios";

const initialState = {
  email: "",
  role: "",
  userOrderList: [],
  isLoading: true,
};

export const getuserOrderList = createAsyncThunk(
  "userOrder/getuserOrderList",
  async (data, thunkAPI) => {
    console.log(data);
    try {
      const resp = await axios.get(
        `http://${process.env.REACT_APP_API_URL}/order/${data.role}`,
        { actorId: data.actorId }
      );
      return resp.data;
    } catch (error) {
      return thunkAPI.rejectWithValue("something went wrong");
    }
  }
);

const userOrderSlice = createSlice({
  name: "userOrders",
  initialState,
  reducers: {
    setUserEmail: (state, action) => {
      state.email = action.payload;
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(getuserOrderList.pending, (state) => {
        state.isLoading = true;
      })
      .addCase(getuserOrderList.fulfilled, (state, action) => {
        state.userOrderList = action.payload;
        state.isLoading = false;
      })
      .addCase(getuserOrderList.rejected, (state, action) => {
        state.isLoading = false;
      });
  },
});

export default userOrderSlice.reducer;

export const { setUserEmail } = userOrderSlice.actions;
