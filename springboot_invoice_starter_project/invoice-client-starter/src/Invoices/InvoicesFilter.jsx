import React, { useState } from "react";

const InvoicesFilter = ({ onFilter }) => {
  const [form, setForm] = useState({
    buyerId: "",
    sellerId: "",
    product: "",
    minPrice: "",
    maxPrice: "",
    limit: "",
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setForm((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleFilter = () => {
    // Odebereme prázdné hodnoty a u čísel převedeme na Number
    const filteredParams = Object.fromEntries(
      Object.entries(form)
        .filter(([_, v]) => v !== "")
        .map(([k, v]) => {
          if (["buyerId", "sellerId", "minPrice", "maxPrice", "limit"].includes(k)) {
            return [k, Number(v)];
          }
          return [k, v];
        })
    );

    onFilter(filteredParams);  // Předáme filtr rodiči
  };

  return (
    <div className="filter-form-wrapper">
      <h2>Filtrovat faktury</h2>
      <div className="filter-form">
        <input
          type="number"
          name="sellerId"
          placeholder="ID prodávajícího"
          value={form.sellerId}
          onChange={handleChange}
          min="0"
        />
        <input
          type="number"
          name="buyerId"
          placeholder="ID kupujícího"
          value={form.buyerId}
          onChange={handleChange}
          min="0"
        />
        <input
          type="text"
          name="product"
          placeholder="Produkt"
          value={form.product}
          onChange={handleChange}
        />
        <input
          type="number"
          name="minPrice"
          placeholder="Minimální cena"
          value={form.minPrice}
          onChange={handleChange}
          min="0"
        />
        <input
          type="number"
          name="maxPrice"
          placeholder="Maximální cena"
          value={form.maxPrice}
          onChange={handleChange}
          min="0"
        />
        <input
          type="number"
          name="limit"
          placeholder="Limit výsledků"
          value={form.limit}
          onChange={handleChange}
          min="1"
        />
        <button onClick={handleFilter}>Filtrovat</button>
      </div>
    </div>
  );
};

export default InvoicesFilter;
