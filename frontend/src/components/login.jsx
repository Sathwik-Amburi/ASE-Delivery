import React, { useState } from "react";
import Avatar from "@mui/material/Avatar";
import Button from "@mui/material/Button";
import CssBaseline from "@mui/material/CssBaseline";
import TextField from "@mui/material/TextField";
import FormControlLabel from "@mui/material/FormControlLabel";
import Checkbox from "@mui/material/Checkbox";
import Link from "@mui/material/Link";
import Box from "@mui/material/Box";
import Grid from "@mui/material/Grid";
import LockOutlinedIcon from "@mui/icons-material/LockOutlined";
import Typography from "@mui/material/Typography";
import LandingPage from "./landingPage";
import Copyright from "./copyright";
import { useNavigate } from "react-router-dom";
import { useDispatch } from "react-redux";
import { updateUser } from "../features/user/userSlice";

export default function SignInSide() {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const [userRole, setUserRole] = useState("dispatcher");
  const handleSubmit = (event) => {
    event.preventDefault();
    const data = new FormData(event.currentTarget);
    console.log({
      email: data.get("email"),
      password: data.get("password"),
      role: userRole,
    });
    const formData = {
      id: "123",
      email: data.get("email"),
      role: userRole,
    };
    dispatch(updateUser(formData));
    if (userRole === "dispatcher") {
      navigate("/dispatcher");
    } else if (userRole === "customer") {
      navigate("/customer");
    } else if (userRole === "deliverer") {
      navigate("/deliverer");
    }
  };

  return (
    <Grid container component="main" sx={{ height: "100vh" }}>
      <CssBaseline />
      <Grid item xs={false} sm={4} md={7}>
        <LandingPage />
      </Grid>
      <Grid item xs={12} sm={8} md={5} elevation={6}>
        <Box
          sx={{
            my: 8,
            mx: 4,
            display: "flex",
            flexDirection: "column",
            alignItems: "center",
          }}
        >
          <Avatar sx={{ m: 1, bgcolor: "secondary.main" }}>
            <LockOutlinedIcon />
          </Avatar>
          <Typography component="h1" variant="h5">
            Sign in
          </Typography>
          <Box>
            <Button
              variant={userRole === "dispatcher" ? "contained" : "outlined"}
              onClick={() => setUserRole("dispatcher")}
            >
              Dispatcher
            </Button>
            <Button
              variant={userRole === "customer" ? "contained" : "outlined"}
              onClick={() => setUserRole("customer")}
            >
              Customer
            </Button>
            <Button
              variant={userRole === "deliverer" ? "contained" : "outlined"}
              onClick={() => setUserRole("deliverer")}
            >
              Deliverer
            </Button>
          </Box>
          <Box
            component="form"
            noValidate={false}
            onSubmit={handleSubmit}
            sx={{ mt: 1 }}
          >
            <TextField
              margin="normal"
              required
              fullWidth
              id="email"
              label="Email Address"
              name="email"
              autoComplete="email"
              autoFocus
            />
            <TextField
              margin="normal"
              required
              fullWidth
              name="password"
              label="Password"
              type="password"
              id="password"
              autoComplete="current-password"
            />
            <FormControlLabel
              control={<Checkbox value="remember" color="primary" />}
              label="Remember me"
            />
            <Button
              type="submit"
              fullWidth
              variant="contained"
              sx={{ mt: 3, mb: 2 }}
            >
              Sign In
            </Button>
            <Grid container>
              <Grid item xs>
                <Link href="#" variant="body2">
                  Forgot password?
                </Link>
              </Grid>
              <Grid item>
                <Link href="/register" variant="body2">
                  {"Don't have an account? Sign Up"}
                </Link>
              </Grid>
            </Grid>
            <Copyright sx={{ mt: 5 }} />
          </Box>
        </Box>
      </Grid>
    </Grid>
  );
}
