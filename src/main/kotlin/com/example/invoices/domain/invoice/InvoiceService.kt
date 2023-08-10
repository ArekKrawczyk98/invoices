package com.example.invoices.domain.invoice

class InvoiceService(
    private val invoiceRepository: InvoiceRepository
) {

    fun create(invoice: Invoice) = invoiceRepository.create(invoice)

    fun get(invoiceId: String): Invoice = invoiceRepository.get(InvoiceId(invoiceId))

}
