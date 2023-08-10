package com.example.invoices.infrastructure.item

import com.example.invoices.domain.item.ItemRepository
import com.example.invoices.infrastructure.item.db.JdbcItemRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ItemConfiguration {

    @Bean
    fun itemRepository(): ItemRepository = JdbcItemRepository()
}