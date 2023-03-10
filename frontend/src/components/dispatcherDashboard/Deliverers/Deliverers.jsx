import * as React from "react";
import AddDeliver from "./addDeliverer";
import Table from "./delivererTable";
import { Grid } from "@mui/material";
export default function Orders() {
  return (
    <Grid component="span" container spacing={2}>
      <Grid component="span" xs={12} item>
        <AddDeliver />
      </Grid>
      <Table />
    </Grid>
  );
}
