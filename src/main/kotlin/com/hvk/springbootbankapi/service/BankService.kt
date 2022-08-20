package com.hvk.springbootbankapi.service

import com.hvk.springbootbankapi.datasource.BankDataSource
import com.hvk.springbootbankapi.model.Bank
import org.springframework.stereotype.Service

@Service
class BankService(private val dataSource: BankDataSource) {
    fun getBanks(): Collection<Bank> = dataSource.retrieveBanks()
}
