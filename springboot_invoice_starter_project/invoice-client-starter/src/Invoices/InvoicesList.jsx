import React, { useState, useEffect } from "react";
import axios from "axios";
import InvoicesFilter from "./InvoicesFilter";

function InvoiceList() {
  const [invoices, setInvoices] = useState([]);

const fetchFilteredInvoices = async (filters) => {
  const params = {};

  if (filters.buyerId) params.buyerId = filters.buyerId;
  if (filters.sellerId) params.sellerId = filters.sellerId;
  if (filters.product) params.product = filters.product;
  if (filters.minPrice) params.minPrice = filters.minPrice;
  if (filters.maxPrice) params.maxPrice = filters.maxPrice;
  if (filters.limit) params.limit = filters.limit;

  try {
    const response = await axios.get("/api/invoices", { params });
    setInvoices(response.data);
  } catch (error) {
    console.error("Chyba při načítání faktur:", error);
  }
};

  useEffect(() => {
    fetchFilteredInvoices({ limit: 10 }); // výchozí načtení
  }, []);

  return (
    <div>
      <h2>Seznam faktur</h2>
      <InvoicesFilter onFilter={fetchFilteredInvoices} />

      <ul>
        {invoices.length === 0 && <li>Žádné faktury nenalezeny.</li>}
        {invoices.map((invoice) => (
          <li key={invoice.id}>
            <strong>{invoice.invoiceNumber}</strong> – {invoice.product} –{" "}
            {invoice.price} Kč – {invoice.issued}
          </li>
        ))}
      </ul>
    </div>
  );
}

export default InvoiceList;
