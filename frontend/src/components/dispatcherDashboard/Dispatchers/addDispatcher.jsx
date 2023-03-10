import * as React from "react";
import Button from "@mui/material/Button";
import TextField from "@mui/material/TextField";
import Dialog from "@mui/material/Dialog";
import DialogActions from "@mui/material/DialogActions";
import DialogContent from "@mui/material/DialogContent";
import DialogTitle from "@mui/material/DialogTitle";
import { useDispatch } from "react-redux";
import { Box } from "@mui/material";
import { addDispatcher } from "../../../features/dispatcher/dispatcherSlice";

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
      email: data.get("Email"),
      pass: data.get("Password"),
    };
    dispatch(addDispatcher(formData));
    // Call the function passed through props and pass the form data as an argument
    console.log(formData);
    setOpen(false);
  };
  return (
    <div>
      <Button variant="outlined" onClick={handleClickOpen}>
        Add Dipatcher
      </Button>
      <Dialog open={open} onClose={handleClose}>
        <DialogTitle>Add Dispatcher</DialogTitle>
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
              id="Email"
              label="Email"
              variant="outlined"
              name="Email"
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
