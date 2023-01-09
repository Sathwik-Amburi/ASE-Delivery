import React from "react";
import { Box, Typography } from "@mui/material";

const Landing = () => {
  return (
    <Box
      display="flex"
      alignItems="center"
      justifyContent="center"
      height="100vh"
      flexDirection="column"
      p={2}
    >
      <Typography
        variant="h2"
        sx={{ marginBottom: 2 }}
        fontFamily={"monospace"}
      >
        Angry Fish
      </Typography>
      <Typography
        variant="h5"
        sx={{ marginBottom: 4 }}
        color="secondary"
        fontFamily={"monospace"}
      >
        ASE Delivery
      </Typography>
    </Box>
  );
};

export default Landing;
