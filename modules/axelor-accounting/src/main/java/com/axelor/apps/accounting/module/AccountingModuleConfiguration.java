package com.axelor.apps.accounting.module;

import com.axelor.app.AxelorModule;
import com.axelor.apps.accounting.db.repo.AccountingEntryManagementRepository;
import com.axelor.apps.accounting.db.repo.AccountingEntryRepository;
import com.axelor.apps.accounting.service.*;
import com.axelor.apps.invoicing.service.InvoiceServiceImpl;
import com.axelor.apps.invoicing.service.OrderServiceImpl;

public class AccountingModuleConfiguration extends AxelorModule {
    @Override
    protected void configure() {
        bind(InvoiceAccountingService.class).to(InvoiceAccountingServiceImpl.class);
        bind(OrderServiceImpl.class).to(OrderAccountingServiceImpl.class);
        bind(AccountingEntryService.class).to(AccountingEntryServiceImpl.class);
        bind(InvoiceLineAccountingService.class).to(InvoiceLineAccountingServiceImpl.class);

        bind(InvoiceServiceImpl.class).to(InvoiceAccountingServiceImpl.class);

        bind(AccountingEntryRepository.class).to(AccountingEntryManagementRepository.class);
    }

}
