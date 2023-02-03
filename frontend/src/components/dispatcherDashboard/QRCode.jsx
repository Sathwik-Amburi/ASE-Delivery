import QRCode from 'qrcode.react';
import { useState } from "react";
import { Button } from "@mui/material";

import Modal from 'react-modal';

function MyComponent() {
  const [modalIsOpen, setModalIsOpen] = useState(false);

  return (
    <div>
      <button onClick={() => setModalIsOpen(true)}>GENERATE</button>
      <Modal
        isOpen={modalIsOpen}
        onRequestClose={() => setModalIsOpen(false)}
        >
        <h1>Here is the QR code:</h1>
        <QRCode value="https://www.example.com" />
        <button onClick={() => setModalIsOpen(false)}>Close</button>
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
