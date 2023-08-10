package com.example.invoices.domain.invoice

import com.example.invoices.domain.bank.BankAccount
import com.example.invoices.domain.item.Item
import java.time.LocalDate

data class Invoice(
    val id: InvoiceId,
    val issueDate: LocalDate,
    val saleDate: LocalDate,
    val payment: PaymentMethod,
    val paymentDue: Int,
    val bankAccount: BankAccount,
    val seller: Business,
    val buyer: Business,
    val items: List<Item>
)

enum class PaymentMethod {
    TRANSFER,
    CASH
}

data class InvoiceId(val raw: String)


