import * as React from "react";
import AppBar from "@mui/material/AppBar";
import Box from "@mui/material/Box";
import Toolbar from "@mui/material/Toolbar";
import Typography from "@mui/material/Typography";
import Button from "@mui/material/Button";
import SetMealIcon from "@mui/icons-material/SetMeal";
import IconButton from "@mui/material/IconButton";
import { useSelector } from "react-redux";

export default function ButtonAppBar() {
  const user = useSelector((state) => state.user);
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
          <Typography> {user.role} </Typography>
          {!user.loggedIn && (
            <Button color="inherit" href="/register">
              sign up
            </Button>
          )}
          {user.loggedIn && (
            <Button color="inherit" href="/">
              Log out
            </Button>
          )}
        </Toolbar>
      </AppBar>
    </Box>
  );
}
