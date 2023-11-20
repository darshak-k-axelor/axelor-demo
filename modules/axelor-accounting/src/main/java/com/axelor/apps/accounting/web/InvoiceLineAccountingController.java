package com.axelor.apps.accounting.web;

import com.axelor.apps.accounting.db.Account;
import com.axelor.apps.accounting.service.InvoiceLineAccountingService;
import com.axelor.apps.invoicing.db.Invoice;
import com.axelor.apps.invoicing.db.InvoiceLine;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;
import com.axelor.rpc.Context;
import com.google.inject.Inject;

public class InvoiceLineAccountingController {
    private InvoiceLineAccountingService invoiceLineAccountingService;

    @Inject
    public InvoiceLineAccountingController(InvoiceLineAccountingService invoiceLineAccountingService){
        this.invoiceLineAccountingService = invoiceLineAccountingService;
    }

    public void setDefaultInvoiceLineAccount(ActionRequest request, ActionResponse response){
        Context context = request.getContext();
        InvoiceLine invoiceLine = context.asType(InvoiceLine.class);

        Account account = invoiceLineAccountingService.getAccountForInvoiceLine(invoiceLine);
        response.setValue("invoiceLineAccount", account);
    }

}
