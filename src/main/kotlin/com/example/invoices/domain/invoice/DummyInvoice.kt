package com.example.invoices.domain.invoice

import com.example.invoices.domain.bank.BankAccount
import com.example.invoices.domain.bank.BankAccountId
import com.example.invoices.domain.bank.CountryCode
import com.example.invoices.domain.item.Item
import java.math.BigDecimal
import java.time.LocalDate

class DummyInvoice {

    companion object {
        fun create(): Invoice =
            Invoice(
                InvoiceId("1"),
                LocalDate.now(),
                LocalDate.now(),
                PaymentMethod.TRANSFER,
                10,
                BankAccount(BankAccountId("12406666102956862021225512"), "bank name", CountryCode.PL),
                Business("seller", BusinessAddress("seller loc", "00-025", "gen loc seller"), "0000001"),
                Business("buyer", BusinessAddress("buyer loc", "00-026", "gen loc buyer"), "0000002"),
                listOf(Item("test item", 1, BigDecimal.valueOf(25000L), 0.23))
            )
    }
}
