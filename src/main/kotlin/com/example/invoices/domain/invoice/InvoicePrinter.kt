package com.example.invoices.domain.invoice

interface InvoicePrinter {
    fun print(invoice: Invoice): ByteArray

}
