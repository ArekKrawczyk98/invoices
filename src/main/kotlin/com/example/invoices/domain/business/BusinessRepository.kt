package com.example.invoices.domain.business

import com.example.invoices.domain.invoice.Business

interface BusinessRepository {
    fun get(sellerId: Long): Business
}
