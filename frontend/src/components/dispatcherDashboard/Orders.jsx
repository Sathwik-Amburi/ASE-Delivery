import * as React from "react";
import AddOrder from "./addOrder";
import OrderTable from "./orderTable";
import { Grid } from "@mui/material";

export default function Orders() {
  return (
    <Grid component="span" container spacing={2}>
      <Grid component="span" xs={12} item>
        <AddOrder />
      </Grid>
      <OrderTable />
    </Grid>
  );
}
