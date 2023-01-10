import * as React from "react";
import Box from "@mui/material/Box";
import Tabs from "@mui/material/Tabs";
import Tab from "@mui/material/Tab";
import { Typography } from "@mui/material";
import Orders from "./Orders";
import Customers from "./Customers";
import Dispatchers from "./Dispatchers";
import Deliverers from "./Deliverers";

function TabPanel(props) {
  const { children, value, index, ...other } = props;

  return (
    <div
      role="tabpanel"
      hidden={value !== index}
      id={`simple-tabpanel-${index}`}
      aria-labelledby={`simple-tab-${index}`}
      {...other}
    >
      {value === index && (
        <Box sx={{ p: 3 }}>
          <div>{children}</div>
        </Box>
      )}
    </div>
  );
}

export default function CenteredTabs() {
  const [value, setValue] = React.useState(0);

  const handleChange = (event, newValue) => {
    setValue(newValue);
  };

  return (
    <Box sx={{ width: "100%", bgcolor: "background.paper" }}>
      <Tabs value={value} onChange={handleChange} centered>
        <Tab label="Dispatchers" />
        <Tab label="Orders" />
        <Tab label="Customers" />
        <Tab label="Deliverers" />
      </Tabs>
      <TabPanel value={value} index={0}>
        <Dispatchers />
      </TabPanel>
      <TabPanel value={value} index={1}>
        <Orders />
      </TabPanel>
      <TabPanel value={value} index={2}>
        <Customers />
      </TabPanel>
      <TabPanel value={value} index={3}>
        <Deliverers />
      </TabPanel>
    </Box>
  );
}
