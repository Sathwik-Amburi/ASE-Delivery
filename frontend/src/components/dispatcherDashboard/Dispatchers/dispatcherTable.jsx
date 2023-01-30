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
import Edit from "./editDispatcher";
import { deleteDispatcher } from "../../../features/dispatcher/dispatcherSlice";

export default function BasicTable() {
  const { dispatcherList } = useSelector((state) => state.dispatchers);
  const dispatch = useDispatch();

  return (
    <TableContainer component={Paper}>
      <Table aria-label="simple table">
        <TableHead>
          <TableRow>
            <TableCell>Dispatcher ID</TableCell>
            <TableCell align="right">Dispatcher Email</TableCell>
            <TableCell align="right">Dispatcher Password</TableCell>
            <TableCell align="right">Actions</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {dispatcherList.map((dispatcher) => (
            <TableRow
              key={dispatcher.id}
              sx={{ "&:last-child td, &:last-child th": { border: 0 } }}
            >
              <TableCell component="th" scope="row">
                {dispatcher.id}
              </TableCell>
              <TableCell align="right">{dispatcher.email}</TableCell>
              <TableCell align="right">{dispatcher.pass}</TableCell>

              <TableCell align="right">
                <Edit id={dispatcher.id} />{" "}
                <Button
                  variant="contained"
                  onClick={() =>
                    dispatch(deleteDispatcher({ actorId: dispatcher.id }))
                  }
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
