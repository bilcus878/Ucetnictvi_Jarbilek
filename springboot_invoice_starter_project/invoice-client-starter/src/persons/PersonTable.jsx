/*  _____ _______         _                      _
 * |_   _|__   __|       | |                    | |
 *   | |    | |_ __   ___| |___      _____  _ __| | __  ___ ____
 *   | |    | | '_ \ / _ \ __\ \ /\ / / _ \| '__| |/ / / __|_  /
 *  _| |_   | | | | |  __/ |_ \ V  V / (_) | |  |   < | (__ / /
 * |_____|  |_|_| |_|\___|\__| \_/\_/ \___/|_|  |_|\_(_)___/___|
 *                                _
 *              ___ ___ ___ _____|_|_ _ _____
 *             | . |  _| -_|     | | | |     |  LICENCE
 *             |  _|_| |___|_|_|_|_|___|_|_|_|
 *             |_|
 *
 *   PROGRAMOVÁNÍ  <>  DESIGN  <>  PRÁCE/PODNIKÁNÍ  <>  HW A SW
 *
 * Tento zdrojový kód je součástí výukových seriálů na
 * IT sociální síti WWW.ITNETWORK.CZ
 *
 * Kód spadá pod licenci prémiového obsahu a vznikl díky podpoře
 * našich členů. Je určen pouze pro osobní užití a nesmí být šířen.
 * Více informací na http://www.itnetwork.cz/licence
 */

import React from "react";
import { Link, useNavigate } from "react-router-dom";

const PersonTable = ({ label, items, deletePerson }) => {
  const navigate = useNavigate();

  return (
    <div>
      <p>
        {label} {items.length}
      </p>

      <table className="table table-bordered">
        <thead>
          <tr>
            <th>#</th>
            <th>Jméno</th>
            <th>Akce</th>
            <th>Faktury</th>
          </tr>
        </thead>
        <tbody>
          {items.map((item, index) => (
            <tr key={item._id}>
              <td>{index + 1}</td>
              <td>{item.name}</td>
              <td>
                <div className="btn-group">
                  <Link
                    to={"/persons/show/" + item._id}
                    className="btn btn-sm btn-info"
                  >
                    Zobrazit
                  </Link>
                  <Link
                    to={"/persons/edit/" + item._id}
                    className="btn btn-sm btn-warning"
                  >
                    Upravit
                  </Link>
                  <button
                    onClick={() => deletePerson(item._id)}
                    className="btn btn-sm btn-danger"
                  >
                    Odstranit
                  </button>
                </div>
              </td>
              <td>
                <div className="btn-group">
                    <button
                        onClick={() => navigate("/invoices/sent/" + item._id)}
                        className="invoice-button sent"
                    >
                    Vystavené
                    </button>
                    <button
                        onClick={() => navigate("/invoices/received/" + item._id)}
                        className="invoice-button received"
                    >
                    Přijaté
                    </button>

                </div>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      <Link to={"/persons/create"} className="btn btn-success">
        Nová osoba
      </Link>
    </div>
  );
};

export default PersonTable;
