package com.axelor.apps.invoicing.service;

import com.axelor.apps.invoicing.db.Invoice;
import com.axelor.apps.invoicing.db.InvoiceLine;
import com.axelor.apps.invoicing.db.repo.InvoiceRepository;
import com.google.inject.Inject;
import com.google.inject.persist.Transactional;

import java.math.BigDecimal;
import java.util.List;

public class InvoiceServiceImpl implements  InvoiceService {
    private InvoiceRepository invoiceRepository;

    @Inject
    public InvoiceServiceImpl(InvoiceRepository invoiceRepository){
        this.invoiceRepository = invoiceRepository;
    }
    @Override
    public BigDecimal computeExTaxTotalInvoiceValue(Invoice invoice) {
        return invoice.getInvoiceLineList().stream()
                .map(InvoiceLine::getExTaxTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal computeInvoiceTotal(Invoice invoice) {
        return invoice.getInvoiceLineList().stream()
                .map(InvoiceLine::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Transactional(rollbackOn = {Exception.class})
    @Override
    public void setStateSelectToValidate(Invoice invoice) {
        invoice = invoiceRepository.find(invoice.getId());
        invoice.setStateSelect(InvoiceRepository.STATUS_VALIDATED);

        invoiceRepository.save(invoice);
    }
}
