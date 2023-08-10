package com.example.invoices.infrastructure.business

import com.example.invoices.domain.business.BusinessRepository
import com.example.invoices.infrastructure.business.db.JdbcBusinessRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BusinessConfig {

    @Bean
    fun businessRepository(): BusinessRepository = JdbcBusinessRepository()
}