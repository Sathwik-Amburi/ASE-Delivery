import * as React from "react";
import AppBar from "@mui/material/AppBar";
import Box from "@mui/material/Box";
import Toolbar from "@mui/material/Toolbar";
import Typography from "@mui/material/Typography";
import Button from "@mui/material/Button";
import SetMealIcon from "@mui/icons-material/SetMeal";
import IconButton from "@mui/material/IconButton";

export default function ButtonAppBar() {
  return (
    <Box sx={{ flexGrow: 1 }}>
      <AppBar position="static">
        <Toolbar>
          <IconButton size="large" edge="start" color="inherit">
            <SetMealIcon />
          </IconButton>
          <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
            AngryFish
          </Typography>
          <Button color="inherit" href="/register">
            sign up
          </Button>
        </Toolbar>
      </AppBar>
    </Box>
  );
}
