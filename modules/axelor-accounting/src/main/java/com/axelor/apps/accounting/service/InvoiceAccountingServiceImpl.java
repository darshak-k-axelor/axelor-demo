package com.axelor.apps.accounting.service;

import com.axelor.apps.accounting.db.AccountingEntry;
import com.axelor.apps.accounting.db.AccountingEntryLine;
import com.axelor.apps.invoicing.db.Invoice;
import com.axelor.apps.invoicing.db.InvoiceLine;
import com.axelor.apps.invoicing.db.repo.InvoiceRepository;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InvoiceAccountingServiceImpl implements  InvoiceAccountingService{

    private final InvoiceRepository invoiceRepository;

    @Inject
    public InvoiceAccountingServiceImpl(InvoiceRepository invoiceRepository){
        this.invoiceRepository = invoiceRepository;
    }

    @Transactional(rollbackOn = {Exception.class})
    @Override
    public void generateAccountingEntryForInvoice(Invoice invoice) {
        try {
            Invoice currentInvoice = invoiceRepository.find(invoice.getId());
            AccountingEntry accountingEntry = new AccountingEntry();
            accountingEntry.setInvoiceDate(currentInvoice.getInvoiceDate());
            List<InvoiceLine> invoiceLineList = currentInvoice.getInvoiceLineList();

            List<AccountingEntryLine> accountingEntryLineList = currentInvoice.getInvoiceLineList().stream()
                    .map(invoiceLine -> {
                        AccountingEntryLine entryLine = new AccountingEntryLine();
                        entryLine.setCredit(invoiceLine.getTotal());
                        entryLine.setAccount(invoiceLine.getInvoiceLineAccount());
                        entryLine.setAccountingEntry(accountingEntry);
                        return entryLine;
                    })
                    .collect(Collectors.toList());

            AccountingEntryLine accountingEntryLineForDebit = new AccountingEntryLine();
            accountingEntryLineForDebit.setDebit(currentInvoice.getTotal());
            accountingEntryLineForDebit.setAccount(currentInvoice.getCustomerAccount());
            accountingEntryLineForDebit.setAccountingEntry(accountingEntry);
            accountingEntryLineList.add(accountingEntryLineForDebit);
            accountingEntry.setAccountingEntryLineList(accountingEntryLineList);

            invoiceRepository.save(currentInvoice);
//            accountingAccountingEntryRepositoryEntryRepository.save(accountingEntry);

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public boolean validateInvoiceLines(Invoice invoice) {
        List<InvoiceLine> invoiceLineList = invoice.getInvoiceLineList();

        return invoiceLineList.stream()
                .anyMatch(invoiceLine -> invoiceLine.getInvoiceLineAccount() == null);
    }
}
