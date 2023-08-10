package com.example.invoices.application.invoice

import com.example.invoices.application.shared.LanguageUtils
import com.example.invoices.domain.invoice.Invoice
import com.example.invoices.domain.invoice.InvoicePrinter
import com.lowagie.text.Document
import com.lowagie.text.PageSize
import com.lowagie.text.Paragraph
import com.lowagie.text.pdf.PdfWriter
import java.awt.Font
import java.io.ByteArrayOutputStream
import java.util.*

class DefaultInvoicePrinter(private val languageUtils: LanguageUtils) : InvoicePrinter {

    override fun print(invoice: Invoice): ByteArray {
        //TODO this is only test pdf
        val outputStream = ByteArrayOutputStream()
        val document = Document(PageSize.A4)
        val writer = PdfWriter.getInstance(document, outputStream)
        document.open()
        addHeader(invoice, document)

        document.close()
        writer.close()
        return outputStream.toByteArray()
    }

    private fun emptyLine() = Paragraph("\n")

    private fun addHeader(invoice: Invoice, document: Document) {
        val header = Paragraph("${languageUtils.getLocalizedMessage("invoice.header")} ${invoice.id.raw}")
        header.font.size = 24.0f
        header.font.style = Font.BOLD
        document.add(header)
        document.add(emptyLine())
        val issueDate = Paragraph("${languageUtils.getLocalizedMessage("invoice.issue_date")}: ${invoice.issueDate}")
        issueDate.font.style = Font.BOLD
        document.add(issueDate)
        val saleDate = Paragraph("${languageUtils.getLocalizedMessage("invoice.sale_date")}: ${invoice.issueDate}")
        saleDate.font.style = Font.BOLD
        document.add(saleDate)
        val payment = Paragraph("${languageUtils.getLocalizedMessage("invoice.payment")}: ${languageUtils.getLocalizedMessage("invoice.payment.${invoice.payment.name.lowercase()}")}")
        payment.font.style = Font.BOLD
        document.add(payment)
    }
}