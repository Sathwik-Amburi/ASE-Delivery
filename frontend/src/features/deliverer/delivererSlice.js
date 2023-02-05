import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import axios from "axios";

const initialState = {
  delivererList: [],
  isLoading: true,
};

export const getDeliverers = createAsyncThunk(
  "deliverers/getdeliverers",
  async (name, thunkAPI) => {
    try {
      const resp = await axios.get(
        `http://${process.env.REACT_APP_API_URL}/deliverer`
      );
      return resp.data;
    } catch (error) {
      return thunkAPI.rejectWithValue("something went wrong");
    }
  }
);

export const addDeliverer = createAsyncThunk(
  "deliverers/addDeliverer",
  async (deliverer, thunkAPI) => {
    try {
      const response = await axios.post(
        `http://${process.env.REACT_APP_API_URL}/deliverer`,
        deliverer
      );
      return response.data;
    } catch (error) {
      return thunkAPI.rejectWithValue("something went wrong");
    }
  }
);

export const deleteDeliverer = createAsyncThunk(
  "deliverers/deleteDeliverer",
  async (id, thunkAPI) => {
    try {
      const response = await axios.post(
        `http://${process.env.REACT_APP_API_URL}/deliverer/delete`,
        id
      );
      return response.data;
    } catch (error) {
      return thunkAPI.rejectWithValue("something went wrong");
    }
  }
);

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
  },
  extraReducers: (builder) => {
    builder
      .addCase(getDeliverers.pending, (state) => {
        state.isLoading = true;
      })
      .addCase(getDeliverers.fulfilled, (state, action) => {
        state.isLoading = false;
        state.delivererList = action.payload;
      })
      .addCase(getDeliverers.rejected, (state) => {
        state.isLoading = false;
        state.delivererList = [];
      })
      .addCase(addDeliverer.pending, (state) => {
        state.isLoading = true;
      })
      .addCase(addDeliverer.fulfilled, (state, action) => {
        state.isLoading = false;
        state.delivererList.push(action.payload);
      })
      .addCase(addDeliverer.rejected, (state) => {
        state.isLoading = false;
      })
      .addCase(deleteDeliverer.pending, (state) => {
        state.isLoading = true;
      })
      .addCase(deleteDeliverer.fulfilled, (state, action) => {
        state.isLoading = false;
        state.delivererList = state.delivererList.filter(
          (deliverer) => deliverer._id !== action.payload
        );
      })
      .addCase(deleteDeliverer.rejected, (state) => {
        state.isLoading = false;
      });
  },
});

export default delivererSlice.reducer;
export const { editDeliverer } = delivererSlice.actions;
