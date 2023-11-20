package com.axelor.apps.accounting.web;

import com.axelor.apps.accounting.service.InvoiceAccountingService;
import com.axelor.apps.invoicing.db.Invoice;
import com.axelor.apps.invoicing.db.repo.InvoiceRepository;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;
import com.axelor.rpc.Context;

import javax.inject.Inject;

public class InvoiceAccountingController {
    private final InvoiceAccountingService invoiceAccountingService;

    @Inject
    public InvoiceAccountingController(InvoiceAccountingService invoiceAccountingService) {
        this.invoiceAccountingService = invoiceAccountingService;
    }

    public void generateAccountingEntryForInvoice(ActionRequest request, ActionResponse response){
        Context context = request.getContext();
        Invoice invoice = context.asType(Invoice.class);
        invoiceAccountingService.generateAccountingEntryForInvoice(invoice);
        response.setReload(true);
    }

    public void checkEmptyAccountInInvoiceLines(ActionRequest request, ActionResponse response){
        Context context = request.getContext();
        Invoice invoice = context.asType(Invoice.class);

        boolean emptyInvoiceLine = invoiceAccountingService.validateInvoiceLines(invoice);
        int validateStatus = invoiceAccountingService.getValidateStatus();
        if(emptyInvoiceLine){
            response.setError("At least one invoice line with an account that is empty. Please check again!");
        } else{
            response.setValue("stateSelect", validateStatus);
        }
    }
}
