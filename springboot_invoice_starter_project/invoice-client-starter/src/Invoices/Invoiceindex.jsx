import React, { useEffect, useState } from "react";

import { apiDelete, apiGet } from "../utils/api";

import InvoiceTable from "./InvoiceTable";
import InvoicesFilter from "./InvoicesFilter";

const InvoiceIndex = () => {
  const [invoices, setInvoices] = useState([]);
  const [filter, setFilter] = useState({});

  const deleteInvoice = async (id) => {
    try {
      await apiDelete("/api/invoices/" + id);
      setInvoices(invoices.filter((item) => item._id !== id));
    } catch (error) {
      console.log(error.message);
      alert(error.message);
    }
  };

  const loadInvoices = async () => {
    try {
      // tady posíláme filtr jako query parametry
      const data = await apiGet("/api/invoices", filter);
      setInvoices(data);
    } catch (error) {
      console.log(error.message);
    }
  };

  useEffect(() => {
    loadInvoices();
  }, [filter]); // reload invoices when filter changes

  return (
    <div>
      <h1>Seznam faktur</h1>
      <InvoicesFilter onFilter={setFilter} />
      <InvoiceTable
        deleteInvoice={deleteInvoice}
        items={invoices}
        label="Počet faktur:"
      />
    </div>
  );
};

export default InvoiceIndex;
