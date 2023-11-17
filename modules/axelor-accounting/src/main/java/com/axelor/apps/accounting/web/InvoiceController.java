package com.axelor.apps.accounting.web;

import com.axelor.apps.accounting.service.InvoiceService;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;

import javax.inject.Inject;
import java.time.LocalDate;

public class InvoiceController {
    private final InvoiceService invoiceService;

    @Inject
    public InvoiceController(InvoiceService invoiceService){
        this.invoiceService = invoiceService;
    }

    public void generateAccountingEntry(ActionRequest request, ActionResponse response){

    }

    public void checkEmptyAccountInInvoiceLines(ActionRequest request, ActionResponse response){

    }
}
