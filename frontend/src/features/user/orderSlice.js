import { createSlice } from "@reduxjs/toolkit";

const userOrder = {
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
  userOrderList: [userOrder],
  isLoading: true,
};

const userOrderSlice = createSlice({
  name: "userOrders",
  initialState,
  reducers: {},
});

export default userOrderSlice.reducer;