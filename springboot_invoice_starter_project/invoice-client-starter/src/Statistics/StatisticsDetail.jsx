import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { apiGet } from "../utils/api";

const StatisticsDetail = () => {
  const { id } = useParams();
  const [personStats, setPersonStats] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    apiGet("/api/persons/statistics")
      .then((data) => {
        const stats = data.find((item) => item.personId.toString() === id);
        setPersonStats(stats || null);
      })
      .catch((err) => {
        console.error(err);
      });
  }, [id]);

  if (!personStats) {
    return <p>Načítám statistiky nebo nebyly nalezeny.</p>;
  }

  return (
    <div className="page-container">
      <h1>Statistika osoby</h1>
      <hr />
      <h3>{personStats.personName}</h3>
      <p>
        <strong>Fakturované příjmy:</strong> {personStats.revenue} Kč
      </p>

      {/* Tlačítko ve vlastním wrapperu zarovnaném vpravo */}
      <div className="button-container">
        <button
          onClick={() => navigate("/statistics")}
          className="back-button"
        >
          Zpět
        </button>
      </div>
    </div>
  );
};

export default StatisticsDetail;


