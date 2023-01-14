import * as React from "react";
import Button from "@mui/material/Button";
import TextField from "@mui/material/TextField";
import Dialog from "@mui/material/Dialog";
import DialogActions from "@mui/material/DialogActions";
import DialogContent from "@mui/material/DialogContent";
import DialogTitle from "@mui/material/DialogTitle";
import { useDispatch } from "react-redux";
import { Box } from "@mui/material";
import { addOrder } from "../../../features/order/orderSlice";

export default function FormDialog() {
  const [open, setOpen] = React.useState(false);
  const dispatch = useDispatch();

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };
  const handleSubmit = (event) => {
    event.preventDefault();
    // Get the form data
    const data = new FormData(event.currentTarget);
    const formData = {
      id: data.get("ID"),
      dispatcher: {
        id: "63bd33a9e03f596350f8afb2",
        email: data.get("dispatcherEmail"),
        actorType: "Dispatcher",
      },
      deliverer: {
        id: "63bd33a9e03f596350f8afb3",
        email: data.get("delivererEmail"),
        actorType: "Deliverer",
      },
      client: {
        id: "63bd2d47dea40908ea916896",
        email: data.get("clientEmail"),
        actorType: "Client",
      },
      street: data.get("street"),
      orderStatus: data.get("orderStatus"),
    };
    dispatch(addOrder(formData));
    // Call the function passed through props and pass the form data as an argument
    console.log(formData);
    setOpen(false);
  };
  return (
    <div>
      <Button variant="outlined" onClick={handleClickOpen}>
        Add Order
      </Button>
      <Dialog open={open} onClose={handleClose}>
        <DialogTitle>Edit Order</DialogTitle>
        <DialogContent>
          <Box
            component="form"
            sx={{
              "& .MuiTextField-root": { mb: 1 },
            }}
            noValidate
            autoComplete="off"
            onSubmit={handleSubmit}
          >
            <TextField
              id="ID"
              label="ID"
              variant="outlined"
              name="ID"
              fullWidth
            />
            <TextField
              id="dispatcherEmail"
              label="Dispatcher Email"
              variant="outlined"
              name="dispatcherEmail"
              fullWidth
            />

            <TextField
              id="clientEmail"
              label="Customer Email"
              variant="outlined"
              name="clientEmail"
              fullWidth
            />
            <TextField
              id="delivererEmail"
              label="Deliverer Email"
              variant="outlined"
              name="delivererEmail"
              fullWidth
            />
            <TextField
              id="street"
              label="Street"
              variant="outlined"
              name="street"
              fullWidth
            />
            <TextField
              id="orderStatus"
              label="orderStatus"
              variant="outlined"
              name="orderStatus"
              fullWidth
            />
            <Button type="submit" variant="contained" color="primary">
              Submit
            </Button>
          </Box>
        </DialogContent>
        <DialogActions></DialogActions>
      </Dialog>
    </div>
  );
}