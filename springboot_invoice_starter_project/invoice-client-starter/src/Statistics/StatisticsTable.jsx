import React from "react";
import { Link } from "react-router-dom";

const StatisticsTable = ({ label, items }) => {
  return (
    <div>
      <p>
        {label} {items.length}
      </p>

      <table className="table table-bordered">
        <thead>
          <tr>
            <th>#</th>
            <th>Jméno / Název</th>
            <th>Statistiky</th>
          </tr>
        </thead>
        <tbody>
          {items.map((item, index) => (
            <tr key={item.personId}>
              <td>{index + 1}</td>
              <td>{item.personName}</td>
              
              <td>
                <Link
                  to={"/statistics/show/" + item.personId}
                  className="btn btn-sm btn-info"
                >
                  Zobrazit
                </Link>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default StatisticsTable;
