package com.hvk.springbootbankapi.datasource.mock

import com.hvk.springbootbankapi.datasource.BankDataSource
import com.hvk.springbootbankapi.model.Bank
import org.springframework.stereotype.Repository


@Repository
class MockBankDataSource : BankDataSource {
    override fun retrieveBanks(): Collection<Bank> {
        return listOf(
            Bank("123", 1.0, 1),
            Bank("123", 1.0, 1),
            Bank("123", 1.0, 1),
        )
    }
}