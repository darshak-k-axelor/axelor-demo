package com.axelor.apps.invoicing.web;

import com.axelor.apps.invoicing.db.InvoiceLine;
import com.axelor.apps.invoicing.service.InvoiceLineService;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;
import com.axelor.rpc.Context;

import javax.inject.Inject;
import java.math.BigDecimal;

public class InvoiceLineController {

    private final InvoiceLineService invoiceLineService;

    @Inject
    public InvoiceLineController(InvoiceLineService invoiceLineService){
        this.invoiceLineService = invoiceLineService;
    }

    public void loadInvoiceLineUnitPrice(ActionRequest request, ActionResponse response){
        Context ctx = request.getContext();
        InvoiceLine invoiceLine = ctx.asType(InvoiceLine.class);

        response.setValue("unitPrice", invoiceLine.getProduct().getUnitPrice());

    }

    public void loadInvoiceLineDescription(ActionRequest request, ActionResponse response){
        Context ctx = request.getContext();
        InvoiceLine invoiceLine = ctx.asType(InvoiceLine.class);

        response.setValue("description", invoiceLine.getProduct().getName());
    }

    public void computeInvoiceLineExTaxTotal(ActionRequest request, ActionResponse response){
        Context ctx = request.getContext();
        InvoiceLine invoiceLine = ctx.asType(InvoiceLine.class);


        BigDecimal exTaxTotalValue = invoiceLineService.computeExTaxTotal(invoiceLine);
        response.setValue("exTaxTotal", exTaxTotalValue);
    }

    public void computeInvoiceLineTotal(ActionRequest request, ActionResponse response){
        Context ctx = request.getContext();
        InvoiceLine invoiceLine = ctx.asType(InvoiceLine.class);

        BigDecimal exTaxTotalValue = invoiceLineService.computeTotal(invoiceLine);
        response.setValue("total", exTaxTotalValue);
    }


}
