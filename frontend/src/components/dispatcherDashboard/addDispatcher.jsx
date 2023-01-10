import React from "react";
import Button from "@mui/material/Button";
import TextField from "@mui/material/TextField";
import Dialog from "@mui/material/Dialog";
import DialogContent from "@mui/material/DialogContent";
import DialogContentText from "@mui/material/DialogContentText";
import DialogTitle from "@mui/material/DialogTitle";
import RefreshIcon from "@mui/icons-material/Refresh";
import Box from "@mui/material/Box";
import { useCallback } from "react";

function AddDispatcher({ onSubmit }) {
  const [open, setOpen] = React.useState(false);
  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  const handleSubmit = useCallback(
    (event) => {
      event.preventDefault();
      // Get the form data
      const data = new FormData(event.currentTarget);
      const formData = {
        id: data.get("ID"),
        Name: data.get("Name"),
        Address: data.get("Address"),
        State: data.get("State"),
        CustomerName: data.get("CustomerName"),
        DelivererName: data.get("DelivererName"),
      };

      // Call the function passed through props and pass the form data as an argument
      onSubmit(formData);
    },
    [onSubmit]
  );

  return (
    <>
      <Button variant="outlined" color="primary" onClick={handleClickOpen}>
        Add Dispatcher
      </Button>
      <Dialog
        open={open}
        onClose={handleClose}
        aria-labelledby="form-dialog-title"
      >
        <DialogTitle id="form-dialog-title">Add Box</DialogTitle>
        <DialogContent>
          <DialogContentText>
            To add a new box, please enter the following information:
          </DialogContentText>
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
              id="Name"
              label="Name"
              variant="outlined"
              name="Name"
              fullWidth
            />
            <TextField
              id="Address"
              label="Address"
              variant="outlined"
              name="Address"
              fullWidth
            />
            <TextField
              id="State"
              label="State"
              variant="outlined"
              name="State"
              fullWidth
            />
            <TextField
              id="CustomerName"
              label="Customer Name"
              variant="outlined"
              name="CustomerName"
              fullWidth
            />
            <TextField
              id="DelivererName"
              label="Deliverer Name"
              variant="outlined"
              name="DelivererName"
              fullWidth
            />
            <Button type="submit" variant="contained" color="primary">
              Submit
            </Button>
          </Box>
        </DialogContent>
      </Dialog>
      <Button>
        <RefreshIcon />
      </Button>
    </>
  );
}

export default AddDispatcher;
