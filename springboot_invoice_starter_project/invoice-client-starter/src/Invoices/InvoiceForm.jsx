import React, { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { apiGet, apiPost, apiPut } from "../utils/api";
import InputField from "../components/InputField";
import FlashMessage from "../components/FlashMessage";

const InvoiceForm = ({ editMode = false }) => {
  const navigate = useNavigate();
  const { id } = useParams();

  const [invoice, setInvoice] = useState({
    invoiceNumber: "",
    issued: "",
    dueDate: "",
    product: "",
    price: "",
    vat: "",
    note: "",
    sellerId: "",
    buyerId: ""
  });

  const [status, setStatus] = useState({
    sent: false,
    success: false,
    error: null
  });

useEffect(() => {
  if (editMode && id) {
    apiGet(`/api/invoices/${id}`)
      .then((data) => {
        // Převod datumu na YYYY-MM-DD formát:
        const formatDate = (dateString) => {
          if (!dateString) return "";
          const d = new Date(dateString);
          return d.toISOString().slice(0, 10);
        };

        setInvoice({
          invoiceNumber: data.invoiceNumber?.toString() || "",
          issued: formatDate(data.issued),
          dueDate: formatDate(data.dueDate),
          product: data.product || "",
          price: data.price?.toString() || "",
          vat: data.vat?.toString() || "",
          note: data.note || "",
          sellerId: data.seller?._id?.toString() || "",
          buyerId: data.buyer?._id?.toString() || ""
        });
      })
      .catch(() => {
        setStatus({ sent: true, success: false, error: "Nepodařilo se načíst fakturu." });
      });
  }
}, [editMode, id]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setInvoice((prev) => ({ ...prev, [name]: value }));
  };

  const validate = () => {
    if (
      !invoice.invoiceNumber.trim() ||
      !invoice.issued.trim() ||
      !invoice.dueDate.trim() ||
      !invoice.product.trim() ||
      !invoice.price.trim() ||
      !invoice.vat.trim() ||
      !invoice.sellerId.trim() ||
      !invoice.buyerId.trim()
    ) {
      setStatus({ sent: true, success: false, error: "Vyplňte prosím všechna povinná pole." });
      return false;
    }
    if (
      isNaN(Number(invoice.invoiceNumber)) ||
      isNaN(Date.parse(invoice.issued)) ||
      isNaN(Date.parse(invoice.dueDate)) ||
      isNaN(Number(invoice.price)) ||
      isNaN(Number(invoice.vat)) ||
      isNaN(Number(invoice.sellerId)) ||
      isNaN(Number(invoice.buyerId))
    ) {
      setStatus({ sent: true, success: false, error: "Zkontrolujte správnost číselných a datových hodnot." });
      return false;
    }
    return true;
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    setStatus({ sent: false, success: false, error: null });

    if (!validate()) return;

    const payload = {
      invoiceNumber: Number(invoice.invoiceNumber),
      issued: invoice.issued,
      dueDate: invoice.dueDate,
      product: invoice.product,
      price: Number(invoice.price),
      vat: Number(invoice.vat),
      note: invoice.note,
      seller: { _id: Number(invoice.sellerId) },
      buyer: { _id: Number(invoice.buyerId) }
    };

    const request = editMode
      ? apiPut(`/api/invoices/${id}`, payload)
      : apiPost("/api/invoices", payload);

    request
      .then(() => {
        setStatus({ sent: true, success: true, error: null });
        navigate("/invoices");
      })
      .catch((err) => {
        setStatus({ sent: true, success: false, error: err.message || "Chyba při odesílání faktury." });
      });
  };

  return (
    <div>
      <h1>{editMode ? "Upravit fakturu" : "Nová faktura"}</h1>
      <hr />
      {status.error && <div className="alert alert-danger">{status.error}</div>}
      {status.sent && status.success && (
        <FlashMessage theme="success" text="Faktura byla úspěšně uložena." />
      )}

      <form onSubmit={handleSubmit}>
        <InputField
          required
          type="number"
          name="invoiceNumber"
          label="Číslo faktury"
          value={invoice.invoiceNumber}
          handleChange={handleChange}
        />

        <InputField
          required
          type="date"
          name="issued"
          label="Datum vystavení"
          value={invoice.issued}
          handleChange={handleChange}
        />

        <InputField
          required
          type="date"
          name="dueDate"
          label="Datum splatnosti"
          value={invoice.dueDate}
          handleChange={handleChange}
        />

        <InputField
          required
          type="text"
          name="product"
          label="Produkt/služba"
          value={invoice.product}
          handleChange={handleChange}
        />

        <InputField
          required
          type="number"
          name="price"
          label="Cena bez DPH"
          value={invoice.price}
          handleChange={handleChange}
          step="0.01"
          min="0"
        />

        <InputField
          required
          type="number"
          name="vat"
          label="DPH (%)"
          value={invoice.vat}
          handleChange={handleChange}
          min="0"
          max="100"
        />

        <InputField
          type="text"
          name="note"
          label="Poznámka"
          value={invoice.note}
          handleChange={handleChange}
        />

        <InputField
          required
          type="number"
          name="sellerId"
          label="ID prodávajícího"
          value={invoice.sellerId}
          handleChange={handleChange}
          min="1"
        />

        <InputField
          required
          type="number"
          name="buyerId"
          label="ID kupujícího"
          value={invoice.buyerId}
          handleChange={handleChange}
          min="1"
        />

        <input
          type="submit"
          className="btn btn-primary"
          value={editMode ? "Uložit změny" : "Uložit fakturu"}
        />
      </form>
    </div>
  );
};

export default InvoiceForm;
