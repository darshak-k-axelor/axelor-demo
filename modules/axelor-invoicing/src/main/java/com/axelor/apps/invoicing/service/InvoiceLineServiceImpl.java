package com.axelor.apps.invoicing.service;

import com.axelor.apps.invoicing.db.InvoiceLine;

import java.math.BigDecimal;

public class InvoiceLineServiceImpl implements  InvoiceLineService {

    @Override
    public BigDecimal computeExTaxTotal(InvoiceLine invoiceLine) {
        return invoiceLine.getUnitPrice().multiply(invoiceLine.getQty());
    }

    @Override
    public BigDecimal computeTotal(InvoiceLine invoiceLine) {
        return invoiceLine.getUnitPrice()
                .multiply(invoiceLine.getQty())
                .multiply(
                        invoiceLine.getTaxRate()
                                .add(BigDecimal.valueOf(100))
                                .divide(BigDecimal.valueOf(100))
                );
    }
}
