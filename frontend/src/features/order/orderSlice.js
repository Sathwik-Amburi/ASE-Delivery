import { createSlice } from "@reduxjs/toolkit";

const order = {
  id: "63bd6531f5305824ce4b9854",
  dispatcher: {
    id: "63bd33a9e03f596350f8afb2",
    email: "disp@gmail.ru",
    actorType: "Dispatcher",
  },
  deliverer: {
    id: "63bd33a9e03f596350f8afb3",
    email: "del@gmail.ru",
    actorType: "Deliverer",
  },
  client: {
    id: "63bd2d47dea40908ea916896",
    email: "babushka@gmail.ru",
    actorType: "Client",
  },
  street: "ErsteStraße",
  orderStatus: "OnItsWay",
};

const initialState = {
  orderList: [order],
  isLoading: true,
};

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
    addOrder: (state, action) => {
      const formData = action.payload;
      state.orderList.push(formData);
    },
    deleteOrder: (state, action) => {
      const id = action.payload;
      state.orderList = state.orderList.filter((order) => order.id !== id);
    },
  },
});

export default orderSlice.reducer;
export const { editOrder, addOrder, deleteOrder } = orderSlice.actions;
