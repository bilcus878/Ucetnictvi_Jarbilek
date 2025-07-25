import React from "react";
import { Link } from "react-router-dom";

const InvoiceTable = ({ label, items, deleteInvoice }) => {
  return (
    <div>
      <p>
        {label} {items.length}
      </p>

      <table className="table table-bordered">
        <thead>
          <tr>
            <th>#</th>
            <th>Číslo faktury</th>
            <th>Datum vystavení</th>
            <th colSpan={3}>Akce</th>
          </tr>
        </thead>
        <tbody>
          {items.map((item, index) => (
            <tr key={index + 1}>
              <td>{index + 1}</td>
              <td>{item.invoiceNumber}</td>
              <td>
                {Array.isArray(item.issued)
                  ? `${item.issued[2]}.${item.issued[1]}.${item.issued[0]}`
                  : item.issued || "-"}
              </td>
              <td>
                <div className="btn-group">
                  <Link
                    to={"/invoices/show/" + item._id}
                    className="btn btn-sm btn-info"
                  >
                    Zobrazit
                  </Link>
                  <Link
                    to={"/invoices/edit/" + item._id}
                    className="btn btn-sm btn-warning"
                  >
                    Upravit
                  </Link>
                  <button
                    onClick={() => deleteInvoice(item._id)}
                    className="btn btn-sm btn-danger"
                  >
                    Odstranit
                  </button>
                </div>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      <Link to={"/invoices/create"} className="btn btn-success">
        Nová faktura
      </Link>
    </div>
  );
};

export default InvoiceTable;
