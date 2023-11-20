package com.axelor.apps.accounting.service;

import com.axelor.apps.accounting.db.AccountingEntry;
import com.axelor.apps.accounting.db.AccountingEntryLine;
import com.axelor.apps.accounting.db.repo.AccountingEntryRepository;
import com.google.inject.Inject;

import java.math.BigDecimal;

public class AccountingEntryServiceImpl implements  AccountingEntryService{
    private AccountingEntryRepository accountingEntryRepository;

    @Inject
    public AccountingEntryServiceImpl(AccountingEntryRepository accountingEntryRepository){
        this.accountingEntryRepository = accountingEntryRepository;
    }


    @Override
    public boolean isSumOfDebitAndCreditSame(AccountingEntry accountingEntry) {
        BigDecimal debitSum = accountingEntry.getAccountingEntryLineList()
                .parallelStream()
                .map(AccountingEntryLine::getDebit)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal creditSum = accountingEntry.getAccountingEntryLineList()
                .parallelStream()
                .map(AccountingEntryLine::getCredit)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return debitSum.equals(creditSum);
    }
}
