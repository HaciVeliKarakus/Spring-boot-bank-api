package com.hvk.springbootbankapi.model

data class Bank(
    val accountNumber: String,
    val trust: Double,
    val transactionFee: Int
)