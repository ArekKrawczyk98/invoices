package com.example.invoices.infrastructure.item.db

import com.example.invoices.domain.item.Item
import com.example.invoices.domain.item.ItemRepository

class JdbcItemRepository: ItemRepository {
    override fun getAllByIds(existingItems: List<Long>): Item {
        TODO("Not yet implemented")
    }
}