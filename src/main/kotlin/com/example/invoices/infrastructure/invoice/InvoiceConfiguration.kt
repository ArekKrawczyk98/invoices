package com.example.invoices.infrastructure.invoice

import com.example.invoices.application.invoice.DefaultInvoicePrinter
import com.example.invoices.application.invoice.DefaultInvoiceRequestProcessor
import com.example.invoices.application.invoice.InvoiceFacade
import com.example.invoices.application.invoice.InvoiceRequestProcessor
import com.example.invoices.application.invoice.InvoiceRequestValidator
import com.example.invoices.application.shared.LanguageUtils
import com.example.invoices.domain.bank.BankRepository
import com.example.invoices.domain.business.BusinessRepository
import com.example.invoices.domain.invoice.InvoicePrinter
import com.example.invoices.domain.invoice.InvoiceRepository
import com.example.invoices.domain.invoice.InvoiceService
import com.example.invoices.domain.item.ItemRepository
import com.example.invoices.infrastructure.invoice.db.JdbcInvoiceRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate

@Configuration
class InvoiceConfiguration {

    @Bean
    fun invoiceRepository(jdbcTemplate: NamedParameterJdbcTemplate): InvoiceRepository =
        JdbcInvoiceRepository(jdbcTemplate)


    @Bean
    fun invoiceRequestProcessor(
        bankRepository: BankRepository,
        businessRepository: BusinessRepository,
        itemRepository: ItemRepository,
        invoiceRepository: InvoiceRepository
    ): InvoiceRequestProcessor =
        DefaultInvoiceRequestProcessor(bankRepository, businessRepository, itemRepository, invoiceRepository)

    @Bean
    fun invoiceService(invoiceRepository: InvoiceRepository): InvoiceService =
        InvoiceService(invoiceRepository)

    @Bean
    fun invoicePrinter(languageUtils: LanguageUtils): InvoicePrinter = DefaultInvoicePrinter(languageUtils)

    @Bean
    fun invoiceFacade(
        processor: InvoiceRequestProcessor,
        service: InvoiceService,
        printer: InvoicePrinter
    ): InvoiceFacade = InvoiceFacade(processor, service, printer, InvoiceRequestValidator())
}