import * as React from "react";
import Button from "@mui/material/Button";
import TextField from "@mui/material/TextField";
import Dialog from "@mui/material/Dialog";
import DialogActions from "@mui/material/DialogActions";
import DialogContent from "@mui/material/DialogContent";
import DialogTitle from "@mui/material/DialogTitle";
import { useDispatch } from "react-redux";
import { Box } from "@mui/material";
import { editDispatcher } from "../../../features/dispatcher/dispatcherSlice";

export default function FormDialog({ id }) {
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
      id: id,
      email: data.get("dispatcherEmail"),
      pass: data.get("Password"),
    };
    dispatch(editDispatcher(formData));
    // Call the function passed through props and pass the form data as an argument
    console.log(formData);
    setOpen(false);
  };
  return (
    <div>
      <Button onClick={handleClickOpen}>Edit</Button>
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
              id="Password"
              label="Password"
              variant="outlined"
              name="Password"
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
