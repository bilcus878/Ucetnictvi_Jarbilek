import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { apiGet } from "../utils/api";

const InvoiceDetail = () => {
  const { id } = useParams();
  const navigate = useNavigate(); // ← tady přidej useNavigate
  const [invoice, setInvoice] = useState(null);

  useEffect(() => {
    apiGet("/api/invoices/" + id)
      .then(data => setInvoice(data))
      .catch(err => {
        console.error(err);
        alert("Nepodařilo se načíst detail faktury.");
      });
  }, [id]);

  if (!invoice) return <p>Načítám fakturu...</p>;

  return (
    <>
      <div className="page-container">
      <div>
        <h1>Detail faktury</h1>
        <hr />
        <h3>Faktura č. {invoice.invoiceNumber}</h3>
        <p>
          <strong>Datum vystavení:</strong><br />
          {invoice.issued}
        </p>
        <p>
          <strong>Datum splatnosti:</strong><br />
          {invoice.dueDate}
        </p>
        <p>
          <strong>Částka:</strong><br />
          {invoice.price} Kč
        </p>
        <p>
          <strong>DPH:</strong><br />
          {invoice.vat} %
        </p>
        <p>
          <strong>Poznámka:</strong><br />
          {invoice.note}
        </p>
        <p>
          <strong>Dodavatel:</strong><br />
          {invoice.seller?.name} ({invoice.seller?.identificationNumber})
        </p>
        <p>
          <strong>Odběratel:</strong><br />
          {invoice.buyer?.name} ({invoice.buyer?.identificationNumber})
        </p>
      </div>
      <div className="button-container">
        <button
          onClick={() => navigate("/invoices")}
          className="back-button"
        >
          Zpět
        </button>
      </div>
      </div>
    </>
  );
};

export default InvoiceDetail;

