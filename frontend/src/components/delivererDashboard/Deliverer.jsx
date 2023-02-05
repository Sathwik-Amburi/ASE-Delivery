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
import {
  setUserEmail,
  getUserData,
  getuserOrderList,
} from "../../features/user/orderSlice";
import { useEffect } from "react";
import QRCode from "./QRCode";

export default function BasicTable() {
  const dispatch = useDispatch();
  const email = localStorage.getItem("email");
  const { userOrderList } = useSelector((state) => state.usertOrders);
  const { id } = useSelector((state) => state.usertOrders);

  dispatch(setUserEmail(email));

  useEffect(() => {
    dispatch(getUserData({ role: "deliverer", email: email }));
  }, [dispatch, email]);

  useEffect(() => {
    dispatch(getuserOrderList({ role: "deliverer", actorId: id }));
  }, [dispatch, id]);

  return (
    <TableContainer>
      <Table aria-label="simple table">
        <TableHead>
          <TableRow>
            <TableCell>Order ID</TableCell>
            <TableCell align="right">Dispatcher Email</TableCell>
            <TableCell align="right">Deliverer Email</TableCell>
            <TableCell align="right">Customer Email</TableCell>
            <TableCell align="right">Street</TableCell>
            <TableCell align="right">Order Status</TableCell>
            <TableCell align="right">QR Code</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {userOrderList.map((order) => (
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
                <QRCode orderId={order.id} />
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
}
