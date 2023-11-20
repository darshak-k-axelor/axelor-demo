package com.axelor.apps.accounting.web;

import com.axelor.apps.accounting.service.InvoiceAccountingService;
import com.axelor.apps.invoicing.db.Invoice;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;
import com.axelor.rpc.Context;

import javax.inject.Inject;

public class InvoiceAccountingController {
    private final InvoiceAccountingService invoiceService;

    @Inject
    public InvoiceAccountingController(InvoiceAccountingService invoiceService) {
        this.invoiceService = invoiceService;
    }

    public void generateAccountingEntryForInvoice(ActionRequest request, ActionResponse response){
        Context context = request.getContext();
        Invoice invoice = context.asType(Invoice.class);
        invoiceService.generateAccountingEntryForInvoice(invoice);
        response.setReload(true);
    }

    public void checkEmptyAccountInInvoiceLines(ActionRequest request, ActionResponse response){
        Context context = request.getContext();
        Invoice invoice = context.asType(Invoice.class);

        boolean emptyInvoiceLine = invoiceService.validateInvoiceLines(invoice);

        if(emptyInvoiceLine){
            response.setError("At least one invoice line with an account that is empty. Please check again!");
        }
    }
}
