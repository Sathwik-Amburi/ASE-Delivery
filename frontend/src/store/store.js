import { configureStore } from "@reduxjs/toolkit";
import orderReducer from "../features/order/orderSlice";
import dispatcherReducer from "../features/dispatcher/dispatcherSlice";
import clientReducer from "../features/client/clientSlice";
import delivererReducer from "../features/deliverer/delivererSlice";
import userReducer from "../features/user/userSlice";
import userOrderReducer from "../features/user/orderSlice";

export const store = configureStore({
  reducer: {
    orders: orderReducer,
    dispatchers: dispatcherReducer,
    clients: clientReducer,
    deliverers: delivererReducer,
    user: userReducer,
    usertOrders: userOrderReducer,
  },
});
