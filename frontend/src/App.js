import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import NavBar from "./components/navBar";
import Login from "./components/login";
import Register from "./components/register";
import Dispatcher from "./components/dispatcher/dispatcher";

function App() {
  return (
    <>
      <NavBar />
      <Router>
        <Routes>
          <Route exact path="/" element={<Login />} />
          <Route exact path="/register" element={<Register />} />
          <Route exact path="/dispatcher" element={<Dispatcher />} />
        </Routes>
      </Router>
    </>
  );
}

export default App;
