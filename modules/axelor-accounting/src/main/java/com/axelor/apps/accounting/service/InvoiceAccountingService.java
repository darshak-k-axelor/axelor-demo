package com.axelor.apps.accounting.service;

import com.axelor.apps.invoicing.db.Invoice;

public interface InvoiceAccountingService {
    public void generateAccountingEntryForInvoice(Invoice invoice);

    public boolean validateInvoiceLines(Invoice invoice);
}
