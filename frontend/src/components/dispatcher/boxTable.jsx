import * as React from "react";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Paper from "@mui/material/Paper";
import Boxes from "./sampleData/boxes.json";
import { Button } from "@mui/material";
import AddBox from "./addBox";
import Box from "@mui/material/Box";
import { useState, useCallback } from "react";

let boxes = Boxes.boxes;
export default function BoxTable() {
  const [cBoxes, setCBoxes] = useState([...boxes]);

  const handleFormSubmit = useCallback((data) => {
    setCBoxes([data, ...boxes]);
  }, []);
  return (
    <>
      <AddBox onSubmit={handleFormSubmit} />
      <TableContainer component={Paper}>
        <Box>
          <Table sx={{ minWidth: 650 }}>
            <TableHead>
              <TableRow>
                <TableCell>ID</TableCell>
                <TableCell align="right">Name</TableCell>
                <TableCell align="right">Address</TableCell>
                <TableCell align="right">State</TableCell>
                <TableCell align="right">Customer Name</TableCell>
                <TableCell align="right">Deliverer Name</TableCell>
                <TableCell align="right">Actions</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {cBoxes.map((box) => (
                <TableRow key={boxes.id}>
                  <TableCell component="th" scope="row">
                    {box.id}
                  </TableCell>
                  <TableCell align="right">{box.Name}</TableCell>
                  <TableCell align="right">{box.Address}</TableCell>
                  <TableCell align="right">{box.State}</TableCell>
                  <TableCell align="right">{box.CustomerName}</TableCell>
                  <TableCell align="right">{box.DelivererName}</TableCell>
                  <TableCell align="right">
                    <Button variant="contained">Edit</Button>{" "}
                    <Button variant="contained">Delete</Button>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </Box>
      </TableContainer>
    </>
  );
}
