import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import NavBar from "./components/navBar";
import Login from "./components/login";
import Register from "./components/register";
import Dispatcher from "./components/dispatcherDashboard/Dashboard";
import Deliverer from "./components/delivererDashboard/Deliverer";
import Customer from "./components/customerDashboard/Customer";

function App() {
  return (
    <>
      <NavBar />
      <Router>
        <Routes>
          <Route exact path="/" element={<Login />} />
          <Route exact path="/register" element={<Register />} />
          <Route exact path="/dispatcher" element={<Dispatcher />} />
          <Route exact path="/deliverer" element={<Deliverer />} />
          <Route exact path="/customer" element={<Customer />} />
          <Route path="*" element={<h1>404 Not Found</h1>} />
        </Routes>
      </Router>
    </>
  );
}

export default App;
