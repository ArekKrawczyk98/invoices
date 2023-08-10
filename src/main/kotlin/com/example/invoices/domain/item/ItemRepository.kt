package com.example.invoices.domain.item

interface ItemRepository {
    fun getAllByIds(existingItems: List<Long>): Item
}
