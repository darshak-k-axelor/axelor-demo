package com.axelor.apps.invoicing.db.repo;

import com.axelor.apps.invoicing.db.Invoice;
import com.axelor.apps.sales.db.repo.OrderRepository;

public class InvoiceManagementRepository extends  InvoiceRepository{
    @Override
    public Invoice copy(Invoice entity, boolean deep) {
        Invoice copy = super.copy(entity, deep);
        copy.setStateSelect(OrderRepository.STATUS_DRAFT);
        return copy;
    }
}
