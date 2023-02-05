import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import axios from "axios";

const initialState = {
  clientList: [],
  isLoading: true,
};

export const getClients = createAsyncThunk(
  "clients/getClients",
  async (name, thunkAPI) => {
    try {
      const resp = await axios.get(
        `http://${process.env.REACT_APP_API_URL}/client`
      );
      return resp.data;
    } catch (error) {
      return thunkAPI.rejectWithValue("something went wrong");
    }
  }
);

export const addClient = createAsyncThunk(
  "clients/addClient",
  async (client, thunkAPI) => {
    try {
      const response = await axios.post(
        `http://${process.env.REACT_APP_API_URL}/client`,
        client
      );
      return response.data;
    } catch (error) {
      alert("user already exists or something went wrong!\n" + error);
      return thunkAPI.rejectWithValue("something went wrong");
    }
  }
);

export const deleteClient = createAsyncThunk(
  "clients/deleteClient",
  async (id, thunkAPI) => {
    try {
      const response = await axios.post(
        `http://localhost:8080/client/delete`,
        id
      );
      return response.data;
    } catch (error) {
      return thunkAPI.rejectWithValue("something went wrong");
    }
  }
);

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
  },
  extraReducers: (builder) => {
    builder
      .addCase(getClients.pending, (state) => {
        state.isLoading = true;
      })
      .addCase(getClients.fulfilled, (state, action) => {
        state.isLoading = false;
        state.clientList = action.payload;
      })
      .addCase(getClients.rejected, (state) => {
        state.isLoading = false;
        state.clientList = [];
      })
      .addCase(addClient.pending, (state) => {
        state.isLoading = true;
      })
      .addCase(addClient.fulfilled, (state, action) => {
        state.isLoading = false;
        state.clientList.push(action.payload);
      })
      .addCase(addClient.rejected, (state) => {
        state.isLoading = false;
      })
      .addCase(deleteClient.pending, (state) => {
        state.isLoading = true;
      })
      .addCase(deleteClient.fulfilled, (state, action) => {
        state.isLoading = false;
      })
      .addCase(deleteClient.rejected, (state) => {
        state.isLoading = false;
      });
  },
});

export default clientSlice.reducer;
export const { editClient } = clientSlice.actions;
