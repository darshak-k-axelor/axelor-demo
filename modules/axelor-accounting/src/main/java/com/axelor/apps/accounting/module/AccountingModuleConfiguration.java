package com.axelor.apps.accounting.module;

import com.axelor.app.AxelorModule;
import com.axelor.apps.invoicing.service.InvoiceLineService;
import com.axelor.apps.invoicing.service.InvoiceLineServiceImpl;

public class AccountingModuleConfiguration extends AxelorModule {
    @Override
    protected void configure() {
        bind(InvoiceLineService.class).to(InvoiceLineServiceImpl.class);

    }

}
