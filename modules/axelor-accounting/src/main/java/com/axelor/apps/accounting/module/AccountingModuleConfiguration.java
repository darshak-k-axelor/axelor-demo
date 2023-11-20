package com.axelor.apps.accounting.module;

import com.axelor.app.AxelorModule;
import com.axelor.apps.accounting.service.InvoiceAccountingService;
import com.axelor.apps.accounting.service.InvoiceAccountingServiceImpl;
import com.axelor.apps.accounting.service.InvoiceLineAccountingService;
import com.axelor.apps.accounting.service.InvoiceLineAccountingServiceImpl;
import com.axelor.apps.invoicing.service.InvoiceLineService;
import com.axelor.apps.invoicing.service.InvoiceLineServiceImpl;

public class AccountingModuleConfiguration extends AxelorModule {
    @Override
    protected void configure() {
        bind(InvoiceAccountingService.class).to(InvoiceAccountingServiceImpl.class);
        bind(InvoiceLineAccountingService.class).to(InvoiceLineAccountingServiceImpl.class);

    }

}
