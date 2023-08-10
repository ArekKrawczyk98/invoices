package com.example.invoices.application.invoice

import com.example.invoices.domain.bank.BankAccountId
import com.example.invoices.domain.invoice.DummyInvoice
import com.example.invoices.domain.invoice.InvoicePrinter
import com.example.invoices.domain.invoice.InvoiceService
import com.example.invoices.domain.invoice.PaymentMethod
import com.example.invoices.domain.item.Item
import java.time.LocalDate
import java.util.*

class InvoiceFacade(
    private val invoiceRequestProcessor: InvoiceRequestProcessor,
    private val invoiceService: InvoiceService,
    private val invoicePrinter: InvoicePrinter,
    private val requestValidator: InvoiceRequestValidator,
) {

    fun create(request: InvoiceRequest) {
        requestValidator.invoke(request)
        val invoice = invoiceRequestProcessor.process(request)
        invoiceService.create(invoice)
    }

    fun print(invoiceId: String): ByteArray {
//        val invoice = invoiceService.get(invoiceId)
        val invoice = DummyInvoice.create()
        return invoicePrinter.print(invoice)
    }


}

data class InvoiceRequest(
    val invoiceId: String? = null,
    val sellerId: Long,
    val buyerId: Long,
    val existingItems: List<Long> = emptyList(),
    val items: List<Item> = emptyList(),
    val issueDate: LocalDate,
    val saleDate: LocalDate,
    val payment: PaymentMethod,
    val paymentDue: Int,
    val bankAccountId: BankAccountId
)