package com.example.invoices.application.invoice

import com.example.invoices.application.shared.LanguageUtils
import com.example.invoices.domain.invoice.Invoice
import com.example.invoices.domain.invoice.InvoicePrinter
import com.example.invoices.domain.item.Item
import com.lowagie.text.Cell
import com.lowagie.text.Chunk
import com.lowagie.text.Document
import com.lowagie.text.Element
import com.lowagie.text.Font
import com.lowagie.text.PageSize
import com.lowagie.text.Paragraph
import com.lowagie.text.Phrase
import com.lowagie.text.Table
import com.lowagie.text.pdf.ColumnText
import com.lowagie.text.pdf.PdfContentByte
import com.lowagie.text.pdf.PdfPCell
import com.lowagie.text.pdf.PdfPRow
import com.lowagie.text.pdf.PdfPTable
import com.lowagie.text.pdf.PdfWriter
import java.awt.Color
import java.io.ByteArrayOutputStream
import java.math.BigDecimal
import java.text.DecimalFormat
import kotlin.properties.Delegates


class DefaultInvoicePrinter(private val languageUtils: LanguageUtils) : InvoicePrinter {
    private var columnWidth by Delegates.notNull<Float>()

    override fun print(invoice: Invoice): ByteArray {
        //TODO this is only test pdf
        val outputStream = ByteArrayOutputStream()
        val document = Document(PageSize.A4)
        val writer = PdfWriter.getInstance(document, outputStream)
        document.open()
        columnWidth = (document.right() - document.left()) / 2 - 10
        val directContent = writer.directContent
        addHeader(invoice, document)
        addSellerAndBuyerSection(invoice, document, directContent)
        addItemsSection(invoice, document, directContent)
        addSummarySection(invoice, document, directContent)
        document.close()
        writer.close()
        return outputStream.toByteArray()
    }

    private fun addSummarySection(invoice: Invoice, document: Document, directContent: PdfContentByte) {
        TODO("Not yet implemented")
    }

    private fun addItemsSection(invoice: Invoice, document: Document, directContent: PdfContentByte) {
        val columnText = ColumnText(directContent)
        val table = PdfPTable(6)
        table.widthPercentage = 100f
        createTableHeaders(table, columnText, document)
        invoice.items.forEachIndexed { index, item ->
            table.addCell(index.toString())
            table.addCell(item.name)
            table.addCell(item.quantity.toString())
            table.addCell(item.netPrice.toString())
            table.addCell((item.netPrice.multiply(BigDecimal.valueOf(item.quantity.toLong()))).toString())
            table.addCell(displayVat(item))
        }
        columnText.addElement(table)
        columnText.go()
    }

    private fun displayVat(item: Item): String {
        val vat = item.vat * 100
        val formatted = DecimalFormat("0.#").format(vat)
        return "$formatted%"
    }

    private fun createTableHeaders(
        table: PdfPTable,
        columnText: ColumnText,
        document: Document
    ) {
        table.addCell(PdfPCell(Phrase(languageUtils.getLocalizedMessage("invoice.table.no"))))
        table.addCell(PdfPCell(Phrase(languageUtils.getLocalizedMessage("invoice.table.name"))))
        val quantity = Phrase(languageUtils.getLocalizedMessage("invoice.table.quantity"))
        table.addCell(PdfPCell(quantity))
        val netPrice = Phrase(languageUtils.getLocalizedMessage("invoice.table.netPrice"))
        table.addCell(PdfPCell(netPrice))
        val totalPrice = Phrase(languageUtils.getLocalizedMessage("invoice.table.totalPrice"))
        table.addCell(PdfPCell(totalPrice))
        table.addCell(PdfPCell(Phrase(languageUtils.getLocalizedMessage("invoice.table.vat"))))
        table.setWidths(intArrayOf(30, 100, 35, 70, 70, 25))
        columnText.setSimpleColumn(
            document.left(),
            document.bottom(),
            document.right(),
            document.top() - 300f
        )
    }

    private fun createBankSection(invoice: Invoice): Paragraph {
        val bankAccount = invoice.bankAccount
        return Paragraph("${bankAccount.name}\n${bankAccount.countryCode}${bankAccount.id.display()}", FONT_BOLD)
    }

    private fun addSellerAndBuyerSection(invoice: Invoice, document: Document, directContent: PdfContentByte) {
        val columnTextLeft = ColumnText(directContent)
        val columnTextRight = ColumnText(directContent)

        val seller = Paragraph("${languageUtils.getLocalizedMessage("invoice.seller")}\n", MEDIUM_FONT_BOLD)
        seller.add(Chunk(invoice.seller.address.display()))
        seller.add(Chunk("${languageUtils.getLocalizedMessage("invoice.nip")}: ${invoice.seller.nip}"))

        val buyer = Paragraph("${languageUtils.getLocalizedMessage("invoice.buyer")}\n", MEDIUM_FONT_BOLD)
        buyer.add(Chunk(invoice.buyer.address.display()))
        buyer.add(Chunk("${languageUtils.getLocalizedMessage("invoice.nip")}: ${invoice.buyer.nip}"))

        columnTextLeft.setSimpleColumn(
            document.left(),
            document.bottom(),
            document.left() + columnWidth,
            document.top() - 150f
        )
        columnTextLeft.addElement(seller)
        columnTextLeft.addElement(createBankSection(invoice))
        columnTextLeft.go()

        columnTextRight.setSimpleColumn(
            document.left() + columnWidth,
            document.bottom(),
            document.right(),
            document.top() - 150f
        )
        columnTextRight.addElement(buyer)
        columnTextRight.go()
    }

    private fun addHeader(invoice: Invoice, document: Document) {
        val header =
            Paragraph("${languageUtils.getLocalizedMessage("invoice.header")} ${invoice.id.raw}", HEADER_FONT_BOLD)
        header.alignment = Element.ALIGN_CENTER
        header.emptyLine()
        header.emptyLine()
        document.add(header)

        val issueDate =
            Paragraph("${languageUtils.getLocalizedMessage("invoice.issue_date")}: ${invoice.issueDate}", FONT_BOLD)
        document.add(issueDate)

        val saleDate =
            Paragraph("${languageUtils.getLocalizedMessage("invoice.sale_date")}: ${invoice.saleDate}", FONT_BOLD)
        document.add(saleDate)

        // TODO: SHOULD ENABLE DIFFERENT TIME PERIODS THAN DAYS !!!!
        val payment = Paragraph(
            "${languageUtils.getLocalizedMessage("invoice.payment")}: " +
                    "${languageUtils.getLocalizedMessage("invoice.payment.${invoice.payment.name.lowercase()}")} " +
                    "${invoice.paymentDue} ${languageUtils.getLocalizedMessage("invoice.payment.time.days")} " +
                    "(${invoice.issueDate.plusDays(invoice.paymentDue.toLong())})", FONT_BOLD
        )
        document.add(payment)
    }

    private fun Paragraph.emptyLine() = this.add(Chunk.NEWLINE)

    companion object {
        val FONT_BOLD = Font(Font.HELVETICA, 12f)
        val HEADER_FONT_BOLD = Font(Font.HELVETICA, 24f, Font.BOLD)
        val MEDIUM_FONT_BOLD = Font(Font.HELVETICA, 14f, Font.BOLD)
    }
}