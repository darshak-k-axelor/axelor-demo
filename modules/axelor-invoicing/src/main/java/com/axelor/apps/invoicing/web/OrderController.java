package com.axelor.apps.invoicing.web;

import com.axelor.apps.invoicing.service.OrderService;
import com.axelor.apps.sales.db.Order;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;
import com.axelor.rpc.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

public class OrderController {
    private static final Logger LOG = LoggerFactory.getLogger(OrderController.class);
    protected OrderService orderService;

    @Inject
    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    public void addInvoice(ActionRequest request, ActionResponse response){
        Context ctx = request.getContext();
        Order order = ctx.asType(Order.class);

        orderService.generateInvoiceForOrder(order);

        response.setReload(true);
    }

    public void generateLateOrderInvoice(ActionRequest request, ActionResponse response){
        orderService.generateLateOrderInvoicesForOrder();
    }

}
