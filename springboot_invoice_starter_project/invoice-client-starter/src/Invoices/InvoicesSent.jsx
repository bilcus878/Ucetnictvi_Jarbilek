import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { apiGet } from "../utils/api";

const InvoicesSent = () => {
  const { id } = useParams();
  const [invoices, setInvoices] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    apiGet(`/api/invoices/sent/${id}`)
      .then((data) => {
        setInvoices(data);
      })
      .catch((err) => {
        console.error(err);
        alert("Chyba při načítání vystavených faktur");
      });
  }, [id]);

  return (
    <div>
      <h1>Vystavené faktury osoby {id}</h1>
      <button onClick={() => navigate(-1)} style={{marginBottom: "10px"}}>
        ← Zpět
      </button>
      {invoices.length === 0 ? (
        <p>Žádné vystavené faktury.</p>
      ) : (
        <table className="table table-bordered">
          <thead>
            <tr>
              <th>Číslo faktury</th>
              <th>Vystaveno</th>
              <th>Datum splatnosti</th>
              <th>Produkt</th>
              <th>Cena</th>
              <th>DPH (%)</th>
              <th>Poznámka</th>
              <th>Prodávající</th>
              <th>Kupující</th>
            </tr>
          </thead>
          <tbody>
            {invoices.map((inv) => (
              <tr key={inv._id}>
                <td>{inv.invoiceNumber}</td>
                <td>{inv.issued.join(".")}</td>
                <td>{inv.dueDate.join(".")}</td>
                <td>{inv.product}</td>
                <td>{inv.price} Kč</td>
                <td>{inv.vat}</td>
                <td>{inv.note}</td>
                <td>{inv.seller?.name || "—"}</td>
                <td>{inv.buyer?.name || "—"}</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
};

export default InvoicesSent;
