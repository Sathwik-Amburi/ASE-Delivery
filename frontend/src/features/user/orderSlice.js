import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import axios from "axios";

const initialState = {
  id: "",
  email: "",
  role: "",
  userOrderList: [],
  isLoading: true,
};

export const getUserData = createAsyncThunk(
  "user/getUserId",
  async (data, thunkAPI) => {
    try {
      const resp = await axios.post(
        `http://${process.env.REACT_APP_API_URL}/${data.role}/email`,
        {
          email: data.email,
        }
      );
      return resp.data;
    } catch (error) {
      return thunkAPI.rejectWithValue("something went wrong");
    }
  }
);

export const getuserOrderList = createAsyncThunk(
  "userOrder/getuserOrderList",
  async (data, thunkAPI) => {
    console.log(data);
    try {
      const resp = await axios.post(
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
      })
      .addCase(getUserData.pending, (state) => {
        state.isLoading = true;
      })
      .addCase(getUserData.fulfilled, (state, action) => {
        state.id = action.payload.id;
        state.role = action.payload.role;

        state.isLoading = false;
      })
      .addCase(getUserData.rejected, (state, action) => {
        state.isLoading = false;
      });
  },
});

export default userOrderSlice.reducer;

export const { setUserEmail } = userOrderSlice.actions;
