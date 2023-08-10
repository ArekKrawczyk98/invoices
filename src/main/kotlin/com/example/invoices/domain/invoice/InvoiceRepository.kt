package com.example.invoices.domain.invoice

import java.time.LocalDate

interface InvoiceRepository {
    fun create(invoice: Invoice)
    fun get(invoiceId: InvoiceId): Invoice
    fun update(invoice: Invoice)
    fun delete(invoice: Invoice)
    fun getAll(): List<Invoice>
    fun getInvoicesFromCurrentMonth(): List<Invoice>
}
