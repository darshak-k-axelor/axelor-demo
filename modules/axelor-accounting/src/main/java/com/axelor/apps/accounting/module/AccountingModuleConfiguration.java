package com.axelor.apps.accounting.module;

import com.axelor.app.AxelorModule;
import com.axelor.apps.accounting.service.*;
import com.axelor.apps.invoicing.service.InvoiceLineService;
import com.axelor.apps.invoicing.service.InvoiceLineServiceImpl;
import com.axelor.apps.invoicing.service.OrderServiceImpl;

public class AccountingModuleConfiguration extends AxelorModule {
    @Override
    protected void configure() {
        bind(InvoiceAccountingService.class).to(InvoiceAccountingServiceImpl.class);
        bind(OrderServiceImpl.class).to(OrderAccountingServiceImpl.class);



    }

}
