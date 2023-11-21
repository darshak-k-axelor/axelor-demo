package com.axelor.apps.invoicing.service;

import com.axelor.apps.invoicing.db.Invoice;
import com.axelor.apps.invoicing.db.InvoiceLine;
import com.axelor.apps.invoicing.db.repo.InvoiceRepository;
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

public class OrderServiceImpl implements OrderService{
    private static final Logger LOG = LoggerFactory.getLogger(OrderServiceImpl.class);
    public static final int FETCH_LIMIT = 20;
    protected OrderRepository orderRepository;
    protected InvoiceRepository invoiceRepository;

    @Inject
    public OrderServiceImpl(OrderRepository orderRepository, InvoiceRepository invoiceRepository) {
        this.orderRepository = orderRepository;
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    @Transactional(rollbackOn = {Exception.class})
    public void generateInvoiceForOrder(Order currentOrder) {

        currentOrder = orderRepository.find(currentOrder.getId());

//        ObjectMapper mapper = new ObjectMapper();
//        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
//        Invoice currentInvoice = mapper.convertValue(currentOrder, Invoice.class);

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

        currentOrder.setBillingDate(LocalDate.now());
        currentOrder.setInvoice(currentInvoice);
        currentOrder.setStateSelect(OrderRepository.STATUS_INVOICE_GENERATED);

        orderRepository.save(currentOrder);
        invoiceRepository.save(currentInvoice);
    }

    private List<InvoiceLine> convertOrderLineListToInvoiceLineList(List<OrderLine> orderLineList, Invoice invoice) {
        List<InvoiceLine> resultInvoiceLineList = new ArrayList<>();
        for (OrderLine orderLine : orderLineList) {

//            ObjectMapper mapper = new ObjectMapper();
//            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
//            InvoiceLine invoiceLine = mapper.convertValue(orderLine, InvoiceLine.class);
//            System.out.println(orderLine);
            InvoiceLine invoiceLine = new InvoiceLine();
            invoiceLine.setInvoice(invoice);
            invoiceLine.setProduct(orderLine.getProduct());
            invoiceLine.setDescription(orderLine.getDescription());
            invoiceLine.setQty(orderLine.getQty());
            invoiceLine.setUnitPrice(orderLine.getUnitPrice());
            invoiceLine.setExTaxTotal(orderLine.getExTaxTotal());
            invoiceLine.setTaxRate(orderLine.getTaxRate());
            invoiceLine.setTotal(orderLine.getTotal());
            resultInvoiceLineList.add(invoiceLine);
        }
        return resultInvoiceLineList;
    }



    @Override
    @Transactional(rollbackOn = {Exception.class})
    public int generateLateOrderInvoicesForOrder() {
        int totalInvoiceGenerated = 0;
        Query<Order> query =
                orderRepository
                        .all()
                        .filter("self.invoice = null AND self.forecastBillingDate < :currentDate")
                        .bind("currentDate", LocalDate.now());

        List<Order> lateOrderList = query.fetch(FETCH_LIMIT);

        while (!lateOrderList.isEmpty()) {
            totalInvoiceGenerated = totalInvoiceGenerated + lateOrderList.size();
            generateLateOrderInvoicesForOrder(lateOrderList);

            JPA.clear();
            lateOrderList = query.fetch(FETCH_LIMIT);
        }

        return totalInvoiceGenerated;

    }

    private void generateLateOrderInvoicesForOrder(List<Order> lateOrderList){
        for (Order lateOrder : lateOrderList) {
            generateInvoiceForOrder(lateOrder);
        }
    }
}
