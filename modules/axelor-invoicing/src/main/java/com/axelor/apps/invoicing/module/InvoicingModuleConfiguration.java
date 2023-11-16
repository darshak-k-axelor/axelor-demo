package com.axelor.apps.invoicing.module;

import com.axelor.app.AxelorModule;
import com.axelor.apps.invoicing.db.repo.OrderManagementRepository;
import com.axelor.apps.invoicing.service.*;
import com.axelor.apps.sales.db.repo.OrderRepository;

public class InvoicingModuleConfiguration  extends AxelorModule {
    @Override
    protected void configure() {
        bind(OrderService.class).to(OrderServiceImpl.class);
        bind(InvoiceService.class).to(InvoiceServiceImpl.class);
        bind(InvoiceLineService.class).to(InvoiceLineServiceImpl.class);
        bind(OrderRepository.class).to(OrderManagementRepository.class);

    }

}
