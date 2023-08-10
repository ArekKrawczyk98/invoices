package com.example.invoices.infrastructure.invoice.http

import com.example.invoices.application.invoice.InvoiceFacade
import com.example.invoices.application.invoice.InvoiceRequest
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.LocaleResolver
import java.util.*

@RequestMapping("/invoice")
@RestController
class InvoiceController(
    private val invoiceFacade: InvoiceFacade
) {

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    fun createInvoice(@RequestBody request: InvoiceRequest) {
        invoiceFacade.create(request)
    }

    @GetMapping("{id}/print")
    fun printInvoice(
        @PathVariable("id") invoiceId: String
    ): ResponseEntity<ByteArray> {
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_PDF
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=invoice-${invoiceId}.pdf")
        return ResponseEntity(invoiceFacade.print(invoiceId), headers, HttpStatus.OK)
    }

}