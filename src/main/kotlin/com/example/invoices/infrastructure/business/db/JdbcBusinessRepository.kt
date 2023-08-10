package com.example.invoices.infrastructure.business.db

import com.example.invoices.domain.business.BusinessRepository
import com.example.invoices.domain.invoice.Business

class JdbcBusinessRepository: BusinessRepository {
    override fun get(sellerId: Long): Business {
        TODO("Not yet implemented")
    }
}