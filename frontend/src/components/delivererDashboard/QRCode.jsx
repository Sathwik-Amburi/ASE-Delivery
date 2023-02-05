import QRCode from 'qrcode.react';
import { useState } from "react";
import { Button , Box} from "@mui/material";

import Modal from 'react-modal';

function MyComponent() {
  const [modalIsOpen, setModalIsOpen] = useState(false);
  const qrSize = 270;

  return (
    <div>
      <button 
      style={{
        backgroundColor: "#0000ff",
        color: "white",
        padding: "12px 20px",
        border: "none",
        borderRadius: "4px",
        cursor: "pointer",
        float: "left"
      }}
      onClick={() => setModalIsOpen(true)}>GENERATE</button>
      <Modal
        isOpen={modalIsOpen}
        onRequestClose={() => setModalIsOpen(false)}
        style={{
          content: {
            width: `${qrSize}px`,
            height: `${qrSize}px`,
            top: "50%",
            left: "50%",
            right: "auto",
            bottom: "auto",
            marginRight: "-50%",
            transform: "translate(-50%, -50%)",
            textAlign: "center",
            padding: "20px"
          }
        }}
        >
        <h1>Here is the QR code:</h1>
        <div style={{display: 'flex', justifyContent: 'center', alignItems: 'center', marginTop: "10px"}}>
          <QRCode value="https://www.example.com" />
        </div>

        
        <button 
        style={{
          backgroundColor: "#0000ff",
          color: "white",
          padding: "12px 20px",
          border: "none",
          borderRadius: "4px",
          cursor: "pointer",
          float: "center",
          marginTop: "15px"
        }}
        onClick={() => setModalIsOpen(false)}>Close</button>
      </Modal>
    </div>
  );
}
export default MyComponent;

/*function Qrcode() {
  const [url, setUrl] = useState("love uuu");
  const [qr, setQr] = useState("");
  const GenerateQRCode = () => {
    QRCode.toDataURL(
      url,
      {
        width: 800,
        margin: 2,
        color: {
          // dark: '#335383FF',
          // light: '#EEEEEEFF'
        },
      },
      (err, url) => {
        if (err) return console.error(err);
        console.log(url);
        setQr(url);
      }
    );
  };
  return (
    <div className="app">
      <Button variant="contained" onClick={GenerateQRCode}>
        Generate
      </Button>
      {qr && (
        <>
          <img src={qr} />
        </>
      )}
    </div>
  );
}
export default Qrcode;*/
