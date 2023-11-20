package com.axelor.apps.accounting.service;

import com.axelor.apps.accounting.db.Account;
import com.axelor.apps.invoicing.db.InvoiceLine;

public interface InvoiceLineAccountingService {
    public Account getAccountForInvoiceLine(InvoiceLine invoiceLine);
}
