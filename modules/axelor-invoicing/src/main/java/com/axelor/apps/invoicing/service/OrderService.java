package com.axelor.apps.invoicing.service;

import com.axelor.apps.sales.db.Order;

public interface OrderService{
    public void generateInvoiceForOrder(Order order);
    public int generateLateOrderInvoicesForOrder();
}
