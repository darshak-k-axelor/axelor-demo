package com.axelor.apps.invoicing.service;

import com.axelor.apps.invoicing.db.Invoice;
import com.axelor.apps.invoicing.db.InvoiceLine;

import java.math.BigDecimal;
import java.util.List;

public class InvoiceServiceImpl implements  InvoiceService {
    @Override
    public BigDecimal computeExTaxTotalInvoiceValue(Invoice invoice) {
        return invoice.getInvoiceLineList().stream()
                .map(InvoiceLine::getExTaxTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal computeInvoiceTotal(Invoice invoice) {
        return invoice.getInvoiceLineList().stream()
                .map(InvoiceLine::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
