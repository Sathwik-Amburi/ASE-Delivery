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
import Edit from "./editClient";
import { deleteClient } from "../../../features/client/clientSlice";

export default function BasicTable() {
  const { clientList } = useSelector((state) => state.clients);
  const dispatch = useDispatch();

  return (
    <TableContainer component={Paper}>
      <Table aria-label="simple table">
        <TableHead>
          <TableRow>
            <TableCell>Customer ID</TableCell>
            <TableCell align="right">Customer Email</TableCell>
            <TableCell align="right">Cusotmer Password</TableCell>
            <TableCell align="right">Actions</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {clientList.map((client) => (
            <TableRow
              key={client.id}
              sx={{ "&:last-child td, &:last-child th": { border: 0 } }}
            >
              <TableCell component="th" scope="row">
                {client.id}
              </TableCell>
              <TableCell align="right">{client.email}</TableCell>
              <TableCell align="right">{client.pass}</TableCell>

              <TableCell align="right">
                <Edit id={client.id} />{" "}
                <Button
                  variant="contained"
                  onClick={() => dispatch(deleteClient({ actorId: client.id }))}
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
