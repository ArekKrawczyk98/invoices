package com.example.invoices.infrastructure.invoice.db

import com.example.invoices.domain.invoice.Invoice
import com.example.invoices.domain.invoice.InvoiceId
import com.example.invoices.domain.invoice.InvoiceRepository
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate

class JdbcInvoiceRepository(private val jdbcTemplate: NamedParameterJdbcTemplate) : InvoiceRepository {

    override fun create(invoice: Invoice) {
        TODO("Not yet implemented")
    }

    override fun get(invoiceId: InvoiceId): Invoice {
        TODO("Not yet implemented")
    }

    override fun update(invoice: Invoice) {
        TODO("Not yet implemented")
    }

    override fun delete(invoice: Invoice) {
        TODO("Not yet implemented")
    }

    override fun getAll(): List<Invoice> {
        TODO("Not yet implemented")
    }

    override fun getInvoicesFromCurrentMonth(): List<Invoice> {
        TODO("Not yet implemented")
    }
}