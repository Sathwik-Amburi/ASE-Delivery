import * as React from "react";
import AddCustomer from "./addCustomer";
import Table from "./clientTable";
import { Grid } from "@mui/material";

export default function Orders() {
  return (
    <Grid component="span" container spacing={2}>
      <Grid component="span" xs={12} item>
        <AddCustomer />
      </Grid>
        <Table />
    </Grid>
  );
}
