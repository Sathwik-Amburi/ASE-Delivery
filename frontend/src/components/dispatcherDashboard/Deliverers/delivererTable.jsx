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
import Edit from "./editDeliverer";
import { deleteDeliverer } from "../../../features/deliverer/delivererSlice";

export default function BasicTable() {
  const { delivererList } = useSelector((state) => state.deliverers);
  const dispatch = useDispatch();

  return (
    <TableContainer component={Paper}>
      <Table aria-label="simple table">
        <TableHead>
          <TableRow>
            <TableCell>Deliverer ID</TableCell>
            <TableCell align="right">Deliverer Email</TableCell>
            <TableCell align="right">Deliverer Password</TableCell>
            <TableCell align="right">Actions</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {delivererList.map((deliverer) => (
            <TableRow
              key={deliverer.id}
              sx={{ "&:last-child td, &:last-child th": { border: 0 } }}
            >
              <TableCell component="th" scope="row">
                {deliverer.id}
              </TableCell>
              <TableCell align="right">{deliverer.email}</TableCell>
              <TableCell align="right">{deliverer.pass}</TableCell>

              <TableCell align="right">
                <Edit id={deliverer.id} />{" "}
                <Button
                  variant="contained"
                  onClick={() => dispatch(deleteDeliverer(deliverer.id))}
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
