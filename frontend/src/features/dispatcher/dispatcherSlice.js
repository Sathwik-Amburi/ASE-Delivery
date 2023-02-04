import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import axios from "axios";

const initialState = {
  dispatcherList: [],
  isLoading: true,
};

export const getDispatchers = createAsyncThunk(
  "dispatchers/getDispatchers",
  async (name, thunkAPI) => {
    try {
      const resp = await axios.get(
        `http://${process.env.REACT_APP_API_URL}/dispatcher`
      );
      return resp.data;
    } catch (error) {
      return thunkAPI.rejectWithValue("something went wrong");
    }
  }
);

export const addDispatcher = createAsyncThunk(
  "dispatchers/addDispatcher",
  async (dispatcher, thunkAPI) => {
    try {
      const response = await axios.post(
        `http://${process.env.REACT_APP_API_URL}/dispatcher`,
        dispatcher
      );
      return response.data;
    } catch (error) {
      return thunkAPI.rejectWithValue("something went wrong");
    }
  }
);

export const deleteDispatcher = createAsyncThunk(
  "dispatchers/deleteDispatcher",
  async (id, thunkAPI) => {
    try {
      const response = await axios.post(
        `http://${process.env.REACT_APP_API_URL}/dispatcher/delete`,
        id
      );
      return response.data;
    } catch (error) {
      return thunkAPI.rejectWithValue("something went wrong");
    }
  }
);

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
    deleteDispatcher: (state, action) => {
      const id = action.payload;
      state.dispatcherList = state.dispatcherList.filter(
        (dispatcher) => dispatcher.id !== id
      );
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(getDispatchers.pending, (state) => {
        state.isLoading = true;
      })
      .addCase(getDispatchers.fulfilled, (state, action) => {
        state.isLoading = false;
        state.dispatcherList = action.payload;
      })
      .addCase(getDispatchers.rejected, (state) => {
        state.isLoading = false;
        state.dispatcherList = [];
      })
      .addCase(addDispatcher.pending, (state) => {
        state.isLoading = true;
      })
      .addCase(addDispatcher.fulfilled, (state, action) => {
        state.isLoading = false;
        state.dispatcherList.push(action.payload);
      })
      .addCase(addDispatcher.rejected, (state) => {
        state.isLoading = false;
      })
      .addCase(deleteDispatcher.pending, (state) => {
        state.isLoading = true;
      })
      .addCase(deleteDispatcher.fulfilled, (state, action) => {
        state.isLoading = false;
      })
      .addCase(deleteDispatcher.rejected, (state) => {
        state.isLoading = false;
      });
  },
});
export default dispatcherSlice.reducer;
export const { editDispatcher } = dispatcherSlice.actions;
