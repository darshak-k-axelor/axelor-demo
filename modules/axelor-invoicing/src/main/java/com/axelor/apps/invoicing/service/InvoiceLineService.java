package com.axelor.apps.invoicing.service;

import com.axelor.apps.invoicing.db.InvoiceLine;

import java.math.BigDecimal;

public interface InvoiceLineService {

    public BigDecimal computeExTaxTotal(InvoiceLine invoiceLine);
    public BigDecimal computeTotal(InvoiceLine invoiceLine);
}
