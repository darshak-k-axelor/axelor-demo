package com.axelor.apps.accounting.service;

import com.axelor.apps.accounting.db.AccountingEntry;

public interface AccountingEntryService {
    public boolean isSumOfDebitAndCreditSame(AccountingEntry accountingEntry);
}
