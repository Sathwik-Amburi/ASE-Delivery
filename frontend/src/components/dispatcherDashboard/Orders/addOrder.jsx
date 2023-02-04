import * as React from "react";
import Button from "@mui/material/Button";
import TextField from "@mui/material/TextField";
import Dialog from "@mui/material/Dialog";
import DialogActions from "@mui/material/DialogActions";
import DialogContent from "@mui/material/DialogContent";
import DialogTitle from "@mui/material/DialogTitle";
import { useDispatch, useSelector } from "react-redux";
import { Box, FormControl, Select, MenuItem, InputLabel } from "@mui/material";
import { addOrder } from "../../../features/order/orderSlice";

export default function FormDialog() {
  const [open, setOpen] = React.useState(false);
  const dispatch = useDispatch();
  const { dispatcherList } = useSelector((state) => state.dispatchers);
  const { clientList } = useSelector((state) => state.clients);
  const { delivererList } = useSelector((state) => state.deliverers);

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
      boxNumber: parseInt(data.get("boxNumber")),
      dispatcherId: data.get("dispatcherEmail"),
      delivererId: data.get("delivererEmail"),
      clientId: data.get("clientEmail"),
      street: data.get("street"),
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
        <DialogTitle>Add Order</DialogTitle>
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
            <FormControl fullWidth>
              <TextField
                id="boxNumber"
                label="Box Number"
                variant="outlined"
                name="boxNumber"
                fullWidth
              />
            </FormControl>
            <FormControl fullWidth>
              <InputLabel id="dispatcherEmail">Dispatcher Email</InputLabel>
              <Select
                id="dispatcherEmail"
                label="Dispatcher Email"
                variant="outlined"
                name="dispatcherEmail"
              >
                {dispatcherList.map((dispatcher) => (
                  <MenuItem key={dispatcher.id} value={dispatcher.id}>
                    {dispatcher.email}
                  </MenuItem>
                ))}
              </Select>
            </FormControl>
            <FormControl fullWidth>
              <InputLabel id="clientEmail">Customer Email</InputLabel>
              <Select
                id="clientEmail"
                label="Customer Email"
                variant="outlined"
                name="clientEmail"
              >
                {clientList.map((client) => (
                  <MenuItem key={client.id} value={client.id}>
                    {client.email}
                  </MenuItem>
                ))}
              </Select>
            </FormControl>
            <FormControl fullWidth>
              <InputLabel id="delivererEmail">Deliverer Email</InputLabel>
              <Select
                id="delivererEmail"
                label="Deliverer Email"
                variant="outlined"
                name="delivererEmail"
              >
                {delivererList.map((deliverer) => (
                  <MenuItem key={deliverer.id} value={deliverer.id}>
                    {deliverer.email}
                  </MenuItem>
                ))}
              </Select>
            </FormControl>
            <TextField
              id="street"
              label="Street"
              variant="outlined"
              name="street"
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
