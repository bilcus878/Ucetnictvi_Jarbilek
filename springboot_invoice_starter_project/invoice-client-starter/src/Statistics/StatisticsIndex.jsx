import React, { useEffect, useState } from "react";
import { apiGet } from "../utils/api";

import StatisticsTable from "./StatisticsTable";

const OverallStatistics = ({ stats }) => {
  return (
    <table className="table table-bordered mb-4" style={{ maxWidth: 600 }}>
      <thead>
        <tr>
          <th>Faktury letošní rok (Kč)</th>
          <th>Faktury celkem (Kč)</th>
          <th>Počet faktur</th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <td>{stats.currentYearSum.toLocaleString()}</td>
          <td>{stats.allTimeSum.toLocaleString()}</td>
          <td>{stats.invoicesCount}</td>
        </tr>
      </tbody>
    </table>
  );
};

const StatisticsIndex = () => {
  const [statistics, setStatistics] = useState([]);
  const [overallStats, setOverallStats] = useState(null);

  useEffect(() => {
    // Načteme statistiky osob
    apiGet("/api/persons/statistics")
      .then((data) => setStatistics(data))
      .catch((err) => console.error(err));

    // Načteme celkové statistiky faktur
    apiGet("/api/invoices/statistics")
      .then((data) => setOverallStats(data))
      .catch((err) => console.error(err));
  }, []);

  return (
    <div>
      <h1>Statistiky</h1>
      {overallStats ? (
        <OverallStatistics stats={overallStats} />
      ) : (
        <p>Načítám celkové statistiky...</p>
      )}
      <StatisticsTable items={statistics} label="Počet osob:" />
    </div>
  );
};

export default StatisticsIndex;
