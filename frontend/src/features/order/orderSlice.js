import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import axios from "axios";

const initialState = {
  orderList: [],
  isLoading: true,
};

export const getOrders = createAsyncThunk(
  "orders/getOrders",
  async (name, thunkAPI) => {
    try {
      const resp = await axios.get(
        `http://${process.env.REACT_APP_API_URL}/order`
      );
      return resp.data;
    } catch (error) {
      return thunkAPI.rejectWithValue("something went wrong");
    }
  }
);
export const addOrder = createAsyncThunk(
  "orders/addOrder",
  async (formData, thunkAPI) => {
    try {
      const resp = await axios.post(
        `http://${process.env.REACT_APP_API_URL}/order`,
        formData
      );
      return resp.data;
    } catch (error) {
      return thunkAPI.rejectWithValue("something went wrong");
    }
  }
);

const orderSlice = createSlice({
  name: "orders",
  initialState,
  reducers: {
    editOrder: (state, action) => {
      const {
        id,
        dispatcherEmail,
        delivererEmail,
        clientEmail,
        street,
        orderStatus,
      } = action.payload;
      const order = state.orderList.find((order) => order.id === id);
      if (order) {
        order.dispatcher.email = dispatcherEmail;
        order.deliverer.email = delivererEmail;
        order.client.email = clientEmail;
        order.street = street;
        order.orderStatus = orderStatus;
      }
    },
    deleteOrder: (state, action) => {
      const id = action.payload;
      state.orderList = state.orderList.filter((order) => order.id !== id);
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(getOrders.pending, (state) => {
        state.isLoading = true;
      })
      .addCase(getOrders.fulfilled, (state, action) => {
        state.isLoading = false;
        state.orderList = action.payload;
      })
      .addCase(getOrders.rejected, (state) => {
        state.isLoading = false;
        state.dispatcherList = [];
      })
      .addCase(addOrder.pending, (state) => {
        state.isLoading = true;
      })
      .addCase(addOrder.fulfilled, (state, action) => {
        state.isLoading = false;
      })
      .addCase(addOrder.rejected, (state) => {
        state.isLoading = false;
      });
  },
});

export default orderSlice.reducer;
export const { editOrder, deleteOrder } = orderSlice.actions;
