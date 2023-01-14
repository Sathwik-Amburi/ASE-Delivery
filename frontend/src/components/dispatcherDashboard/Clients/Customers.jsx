import * as React from "react";
import AddCustomer from "./Clients/addCustomer";
import Table from "./table";
import { Grid } from "@mui/material";

export default function Orders() {
  return (
    <Grid component="span" container spacing={2}>
      <Grid component="span" xs={12} item>
        <AddCustomer />
      </Grid>
      <Grid component="span" item>
        <Table />
      </Grid>
    </Grid>
  );
}
