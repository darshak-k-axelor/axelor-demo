package com.axelor.apps.invoicing.web;

import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;

import java.time.LocalDate;


public class InvoiceController {

    public void setDefaultInitValue(ActionRequest request, ActionResponse response){
        response.setValue("forecastBillingDate", LocalDate.now());
    }

}
