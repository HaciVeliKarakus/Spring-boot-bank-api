package com.hvk.springbootbankapi.datasource

import com.hvk.springbootbankapi.model.Bank

interface BankDataSource {
    fun retrieveBanks(): Collection<Bank>
}