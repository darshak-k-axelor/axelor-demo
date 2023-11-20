package com.axelor.apps.accounting.service;

import com.axelor.apps.accounting.db.AccountingEntry;
import com.axelor.apps.accounting.db.AccountingEntryLine;
import com.axelor.apps.accounting.db.repo.AccountingEntryManagementRepository;
import com.axelor.apps.accounting.db.repo.AccountingEntryRepository;
import com.axelor.apps.invoicing.db.Invoice;
import com.axelor.apps.invoicing.db.InvoiceLine;
import com.axelor.apps.invoicing.db.repo.InvoiceRepository;
import com.axelor.apps.invoicing.service.InvoiceServiceImpl;
import com.google.inject.persist.Transactional;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

public class InvoiceAccountingServiceImpl extends InvoiceServiceImpl implements  InvoiceAccountingService{

    private final InvoiceRepository invoiceRepository;
    private final AccountingEntryManagementRepository accountingEntryManagementRepository;

    @Inject
    public InvoiceAccountingServiceImpl(InvoiceRepository invoiceRepository, AccountingEntryManagementRepository accountingEntryManagementRepository){
        super(invoiceRepository);
        this.invoiceRepository = invoiceRepository;
        this.accountingEntryManagementRepository = accountingEntryManagementRepository;
    }


    @Override
    @Transactional(rollbackOn = {Exception.class})
    public void generateAccountingEntryForInvoice(Invoice currentInvoice) {
        try {
            currentInvoice = invoiceRepository.find(currentInvoice.getId());
            AccountingEntry accountingEntry = new AccountingEntry();
            accountingEntry.setInvoiceDate(currentInvoice.getInvoiceDate());

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

            currentInvoice.setStateSelect(InvoiceRepository.STATUS_ACCOUNTING_ENTRY_GENERATED);

            invoiceRepository.save(currentInvoice);
            accountingEntryManagementRepository.save(accountingEntry);

        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public boolean validateInvoiceLines(Invoice invoice) {
        List<InvoiceLine> invoiceLineList = invoice.getInvoiceLineList();

        return invoiceLineList.stream()
                .anyMatch(invoiceLine -> invoiceLine.getInvoiceLineAccount() == null);
    }

    @Override
    public int getValidateStatus(){
        return InvoiceRepository.STATUS_VALIDATED;
    }
}
