import * as React from "react";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Paper from "@mui/material/Paper";
import { useSelector, useDispatch } from "react-redux";
import { Button } from "@mui/material";
import Edit from "./editOrder";
import { deleteOrder } from "../../../features/order/orderSlice";

export default function BasicTable() {
  const { orderList } = useSelector((state) => state.orders);
  const dispatch = useDispatch();

  return (
    <TableContainer component={Paper}>
      <Table aria-label="simple table">
        <TableHead>
          <TableRow>
            <TableCell>Box ID</TableCell>
            <TableCell align="right">Dispatcher Email</TableCell>
            <TableCell align="right">Deliverer Email</TableCell>
            <TableCell align="right">Customer Email</TableCell>
            <TableCell align="right">Street</TableCell>
            <TableCell align="right">Order Status</TableCell>
            <TableCell align="right">Actions</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {orderList.map((order) => (
            <TableRow
              key={order.id}
              sx={{ "&:last-child td, &:last-child th": { border: 0 } }}
            >
              <TableCell component="th" scope="row">
                {order.id}
              </TableCell>
              <TableCell align="right">{order.dispatcher.email}</TableCell>
              <TableCell align="right">{order.deliverer.email}</TableCell>
              <TableCell align="right">{order.client.email}</TableCell>
              <TableCell align="right">{order.street}</TableCell>
              <TableCell align="right">
                <Button variant="outlined">{order.orderStatus}</Button>
              </TableCell>
              <TableCell align="right">
                <Edit id={order.id} />{" "}
                <Button
                  variant="contained"
                  onClick={() => dispatch(deleteOrder({ orderId: order.id }))}
                >
                  Delete
                </Button>
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
}
