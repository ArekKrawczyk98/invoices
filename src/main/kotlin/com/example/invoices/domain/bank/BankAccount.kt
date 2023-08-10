package com.example.invoices.domain.bank

import com.example.invoices.domain.shared.ValidationException

data class BankAccount(
    val id: BankAccountId,
    val name: String,
    val countryCode: CountryCode
)

enum class CountryCode {
    PL
}

data class BankAccountId(val accountNumber: String) {
    init {
        if (accountNumber.length != 26) {
            throw ValidationException("Account number must have 26 digits")
        }
    }

    fun display(): String =
        accountNumber.substring(0, 2) + " " + accountNumber.substring(2).chunked(4).reduce { acc, s ->
            acc.plus(" $s")
        }
}
