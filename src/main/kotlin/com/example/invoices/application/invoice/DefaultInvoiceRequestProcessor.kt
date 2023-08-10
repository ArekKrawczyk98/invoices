package com.example.invoices.application.invoice

import com.example.invoices.domain.bank.BankRepository
import com.example.invoices.domain.business.BusinessRepository
import com.example.invoices.domain.invoice.Invoice
import com.example.invoices.domain.invoice.InvoiceId
import com.example.invoices.domain.invoice.InvoiceRepository
import com.example.invoices.domain.item.Item
import com.example.invoices.domain.item.ItemRepository
import java.time.LocalDate

class DefaultInvoiceRequestProcessor(
    private val bankRepository: BankRepository,
    private val businessRepository: BusinessRepository,
    private val itemRepository: ItemRepository,
    private val invoiceRepository: InvoiceRepository
) : InvoiceRequestProcessor {

    override fun process(request: InvoiceRequest): Invoice {
        val seller = businessRepository.get(request.sellerId)
        val buyer = businessRepository.get(request.buyerId)
        val bankAccount = bankRepository.get(request.bankAccountId)
        val invoiceIdRaw = request.invoiceId ?: generateInvoiceId()
        val items = retrieveItems(request.existingItems, request.items)
        return Invoice(
            InvoiceId(invoiceIdRaw),
            request.issueDate,
            request.saleDate,
            request.payment,
            request.paymentDue,
            bankAccount,
            seller,
            buyer,
            items
        )
    }

    private fun retrieveItems(existingItems: List<Long>, items: List<Item>): List<Item> {
        val listOfItems = mutableListOf<Item>()
        if (existingItems.isNotEmpty()) {
            listOfItems + itemRepository.getAllByIds(existingItems)
        }
        if (items.isNotEmpty()) {
            listOfItems + items
        }
        return listOfItems
    }

    private fun generateInvoiceId(): String {
        val currentMonthInvoices = invoiceRepository.getInvoicesFromCurrentMonth()
        val today = LocalDate.now()
        val defaultInvoiceIdStart = "FV/${today.year}/${today.monthValue}"
        return if (currentMonthInvoices.isEmpty()) {
            "$defaultInvoiceIdStart/1"
        } else {
            val invoicesById = currentMonthInvoices.associateBy({ getLastNumberFromInvoiceId(it.id.raw) }, { it })
            val lastSignificantInvoiceNumber = invoicesById.keys.max()
            "$defaultInvoiceIdStart/$lastSignificantInvoiceNumber"
        }
    }

    private fun getLastNumberFromInvoiceId(raw: String): Int = raw.substring(raw.lastIndexOf("/")).toInt()
}