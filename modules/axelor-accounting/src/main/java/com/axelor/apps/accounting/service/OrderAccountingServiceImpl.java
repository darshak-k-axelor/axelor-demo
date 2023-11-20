package com.axelor.apps.accounting.service;

import com.axelor.apps.invoicing.db.Invoice;
import com.axelor.apps.invoicing.db.InvoiceLine;
import com.axelor.apps.invoicing.db.repo.InvoiceRepository;
import com.axelor.apps.invoicing.service.OrderServiceImpl;
import com.axelor.apps.sales.db.Order;
import com.axelor.apps.sales.db.OrderLine;
import com.axelor.apps.sales.db.repo.OrderRepository;
import com.axelor.db.JPA;
import com.axelor.db.Query;
import com.google.inject.persist.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderAccountingServiceImpl extends OrderServiceImpl {

    @Inject
    public OrderAccountingServiceImpl(OrderRepository orderRepository, InvoiceRepository invoiceRepository) {
        super(orderRepository, invoiceRepository);
        this.orderRepository = orderRepository;
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    @Transactional(rollbackOn = {Exception.class})
    public void generateInvoiceForOrder(Order currentOrder) {
        currentOrder = orderRepository.find(currentOrder.getId());
        Invoice currentInvoice =new Invoice();

        List<OrderLine> orderLineList = currentOrder.getOrderLineList();
        List<InvoiceLine> generatedInvoiceLineList = convertOrderLineListToInvoiceLineList(orderLineList, currentInvoice);

        currentInvoice.setInvoiceDate(LocalDate.now());
        currentInvoice.setOrders(currentOrder);
        currentInvoice.setInvoiceLineList(generatedInvoiceLineList);
        currentInvoice.setCustomer(currentOrder.getCustomer());
        currentInvoice.setInvoiceDate(LocalDate.now());
        currentInvoice.setStateSelect(currentOrder.getStateSelect());
        currentInvoice.setExTaxTotal(currentOrder.getExTaxTotal());
        currentInvoice.setTotal(currentOrder.getTotal());
        currentInvoice.setStateSelect(OrderRepository.STATUS_DRAFT);
        currentInvoice.setCustomerAccount(currentOrder.getCustomerAccount());

        currentOrder.setBillingDate(LocalDate.now());
        currentOrder.setInvoice(currentInvoice);
        currentOrder.setStateSelect(OrderRepository.STATUS_INVOICE_GENERATED);

        orderRepository.save(currentOrder);
        invoiceRepository.save(currentInvoice);
    }

    private List<InvoiceLine> convertOrderLineListToInvoiceLineList(List<OrderLine> orderLineList, Invoice invoice) {
        List<InvoiceLine> resultInvoiceLineList = new ArrayList<>();
        for (OrderLine orderLine : orderLineList) {
            InvoiceLine invoiceLine = new InvoiceLine();
            invoiceLine.setInvoice(invoice);
            invoiceLine.setProduct(orderLine.getProduct());
            invoiceLine.setDescription(orderLine.getDescription());
            invoiceLine.setQty(orderLine.getQty());
            invoiceLine.setUnitPrice(orderLine.getUnitPrice());
            invoiceLine.setExTaxTotal(orderLine.getExTaxTotal());
            invoiceLine.setTaxRate(orderLine.getTaxRate());
            invoiceLine.setTotal(orderLine.getTotal());
            invoiceLine.setInvoiceLineAccount(orderLine.getProduct().getProductAccount());

            resultInvoiceLineList.add(invoiceLine);
        }
        return resultInvoiceLineList;
    }
}
