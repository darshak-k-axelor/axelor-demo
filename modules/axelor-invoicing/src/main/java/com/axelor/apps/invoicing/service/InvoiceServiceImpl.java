package com.axelor.apps.invoicing.service;

import com.axelor.apps.invoicing.db.Invoice;
import com.axelor.apps.invoicing.db.InvoiceLine;

import java.math.BigDecimal;
import java.util.List;

public class InvoiceServiceImpl implements  InvoiceService {
    @Override
    public BigDecimal computeExTaxTotalInvoiceValue(Invoice invoice) {
        List<InvoiceLine> invoiceLineList = invoice.getInvoiceLineList();
        BigDecimal exTaxTotalResult = new BigDecimal(0);

        for(InvoiceLine currentInvoiceLine : invoiceLineList){
            exTaxTotalResult = exTaxTotalResult.add(currentInvoiceLine.getExTaxTotal());
        }

        return exTaxTotalResult;
    }
}
