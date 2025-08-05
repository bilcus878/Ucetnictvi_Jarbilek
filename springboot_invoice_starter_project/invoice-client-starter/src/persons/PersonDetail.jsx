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

import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { apiGet } from "../utils/api";
import Country from "./Country";

const PersonDetail = () => {
  const { id } = useParams();
  const [person, setPerson] = useState({});
  const navigate = useNavigate();

  useEffect(() => {
    apiGet("/api/persons/" + id)
      .then((data) => {
        setPerson(data);
      })
      .catch((err) => {
        console.error(err);
      });
  }, [id]);

  const country =
    Country.CZECHIA === person.country ? "Česká republika" : "Slovensko";

  return (
    <>=
    <div className="page-container">
      <div>
        <h1>Detail osoby</h1>
        <hr />
        <h3>
          {person.name} ({person.identificationNumber})
        </h3>
        <p>
          <strong>DIČ:</strong>
          <br />
          {person.taxNumber}
        </p>
        <p>
          <strong>Bankovní účet:</strong>
          <br />
          {person.accountNumber}/{person.bankCode} ({person.iban})
        </p>
        <p>
          <strong>Tel.:</strong>
          <br />
          {person.telephone}
        </p>
        <p>
          <strong>Mail:</strong>
          <br />
          {person.mail}
        </p>
        <p>
          <strong>Sídlo:</strong>
          <br />
          {person.street}, {person.city}, {person.zip}, {country}
        </p>
        <p>
          <strong>Poznámka:</strong>
          <br />
          {person.note}
        </p>

        {/* 🔽 Přidaná sekce Faktury */}
        <hr />
        <h3>Faktury</h3>
                    <button
                        onClick={() => navigate("/invoices/sent/" + person._id)}
                        className="invoice-button sent"
                    >
                    Vystavené
                    </button>
                    <button
                        onClick={() => navigate("/invoices/received/" + person._id)}
                        className="invoice-button received"
                    >
                    Přijaté
                    </button>
      </div>
            
      <div className="button-container">
        <button
          onClick={() => navigate("/persons")}
          className="back-button"
        >
          Zpět
        </button>
      </div>
      </div>
    </>
  );
};

export default PersonDetail;
