package com.axelor.apps.accounting.service;

import com.axelor.apps.accounting.db.Account;
import com.axelor.apps.invoicing.db.InvoiceLine;
import com.axelor.apps.invoicing.db.repo.InvoiceLineRepository;
import com.axelor.apps.sales.db.Product;
import com.google.inject.Inject;

public class InvoiceLineAccountingServiceImpl implements  InvoiceLineAccountingService{
    private InvoiceLineRepository invoiceLineRepository;

    @Inject
    public InvoiceLineAccountingServiceImpl(InvoiceLineRepository invoiceLineRepository){
        this.invoiceLineRepository = invoiceLineRepository;
    }

    @Override
    public Account getAccountForInvoiceLine(InvoiceLine invoiceLine) {
        invoiceLine = invoiceLineRepository.find(invoiceLine.getId());
        Product productOfInvoiceLine = invoiceLine.getProduct();
        Account account = productOfInvoiceLine.getProductAccount();

        return account;
    }
}
