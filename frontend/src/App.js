import NavBar from "./components/navBar";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Login from "./components/login";
import Register from "./components/reigster";

function App() {
  return (
    <div className="App">
      <NavBar />
      <>
        <Router>
          <Routes>
            {/* <Route exact path="/" element={<DP2NLP />} /> */}
            <Route exact path="/login" element={<Login />} />
            <Route exact path="/register" element={<Register />} />
          </Routes>
        </Router>
      </>
    </div>
  );
}

export default App;
