import * as React from "react";
import AppBar from "@mui/material/AppBar";
import Box from "@mui/material/Box";
import Toolbar from "@mui/material/Toolbar";
import Typography from "@mui/material/Typography";
import Button from "@mui/material/Button";
import SetMealIcon from "@mui/icons-material/SetMeal";
import IconButton from "@mui/material/IconButton";
import { useSelector } from "react-redux";
import { useDispatch } from "react-redux";
import { resetUser } from "../features/user/userSlice";
import { useEffect } from "react";

export default function ButtonAppBar() {
  const dispatch = useDispatch();

  return (
    <Box sx={{ flexGrow: 1 }}>
      <AppBar position="static">
        <Toolbar>
          <IconButton size="large" edge="start" color="inherit" href="/">
            <SetMealIcon />
          </IconButton>
          <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
            AngryFish
          </Typography>
          <Button
            color="inherit"
            href="/"
            onClick={() => {
              localStorage.clear();
              dispatch(resetUser());
            }}
          >
            Log out
          </Button>
        </Toolbar>
      </AppBar>
    </Box>
  );
}
