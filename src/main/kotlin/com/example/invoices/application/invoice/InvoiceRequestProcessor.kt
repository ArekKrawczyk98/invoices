package com.example.invoices.application.invoice

import com.example.invoices.domain.invoice.Invoice

interface InvoiceRequestProcessor {

    fun process(request: InvoiceRequest): Invoice

}
