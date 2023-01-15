import * as React from "react";
import Box from "@mui/material/Box";
import Tabs from "@mui/material/Tabs";
import Tab from "@mui/material/Tab";
import Orders from "./Orders/Orders";
import Customers from "./Clients/Customers";
import Dispatchers from "./Dispatchers/Dispatchers";
import Deliverers from "./Deliverers/Deliverers";
import { getClients } from "../../features/client/clientSlice";
import { useDispatch } from "react-redux";
import { useEffect } from "react";
import { getDeliverers } from "../../features/deliverer/delivererSlice";
import { getDispatchers } from "../../features/dispatcher/dispatcherSlice";

function TabPanel(props) {
  const dispatch = useDispatch();
  useEffect(() => {
    dispatch(getClients());
  }, []); // eslint-disable-line react-hooks/exhaustive-deps
  useEffect(() => {
    dispatch(getDeliverers());
  }, []); // eslint-disable-line react-hooks/exhaustive-deps
  useEffect(() => {
    dispatch(getDispatchers());
  }, []); // eslint-disable-line react-hooks/exhaustive-deps

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
