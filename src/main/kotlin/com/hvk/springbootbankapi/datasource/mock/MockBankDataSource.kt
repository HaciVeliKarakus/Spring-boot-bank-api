package com.hvk.springbootbankapi.datasource.mock

import com.hvk.springbootbankapi.datasource.BankDataSource
import com.hvk.springbootbankapi.model.Bank
import org.springframework.stereotype.Repository


@Repository
class MockBankDataSource : BankDataSource {
    val banks = listOf(
        Bank("123", 1.0, 1),
        Bank("234", 2.0, 2),
        Bank("345", 3.0, 3),
    )

    override fun retrieveBanks(): Collection<Bank> = banks

    override fun retrieveBank(accountNumber: String): Bank =
        banks.firstOrNull() { it.accountNumber == accountNumber }
            ?: throw NoSuchElementException("Could not find a bank with account number $accountNumber")
}