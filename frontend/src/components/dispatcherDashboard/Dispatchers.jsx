import * as React from "react";
import AddDiapatcher from "./addDispatcher";
import Table from "./table";
import { Grid } from "@mui/material";

export default function Dispatchers() {
  return (
    <Grid component="span" container spacing={2}>
      <Grid component="span" xs={12} item>
        <AddDiapatcher />
      </Grid>
      <Table />
    </Grid>
  );
}
