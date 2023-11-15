package com.axelor.apps.invoicing.web;

import com.axelor.apps.invoicing.db.Invoice;
import com.axelor.apps.invoicing.db.InvoiceLine;
import com.axelor.apps.invoicing.service.InvoiceService;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;
import com.axelor.rpc.Context;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


public class InvoiceController {

    private  InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService){
        this.invoiceService = invoiceService;
    }

    public void setDefaultInitValue(ActionRequest request, ActionResponse response){
        response.setValue("invoiceDate", LocalDate.now());
    }

    public void computeExTaxTotalInvoiceValue(ActionRequest request, ActionResponse response){

        Context ctx = request.getContext();
        Invoice invoice = ctx.asType(Invoice.class);
        BigDecimal exTaxTotalResult = invoiceService.computeExTaxTotalInvoiceValue(invoice);
        response.setValue("exTaxTotal", exTaxTotalResult);
    }

}
