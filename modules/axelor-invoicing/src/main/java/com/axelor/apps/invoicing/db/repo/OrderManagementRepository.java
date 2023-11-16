package com.axelor.apps.invoicing.db.repo;

import com.axelor.apps.sales.db.Order;
import com.axelor.apps.sales.db.repo.OrderRepository;

public class OrderManagementRepository extends OrderRepository {
    @Override
    public Order copy(Order entity, boolean deep) {
        Order copy = super.copy(entity, deep);
        copy.setStateSelect(OrderRepository.STATUS_DRAFT);
        return copy;
    }

}
