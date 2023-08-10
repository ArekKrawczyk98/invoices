package com.example.invoices.infrastructure.bank

import com.example.invoices.domain.bank.BankService
import com.example.invoices.domain.bank.BankRepository
import com.example.invoices.infrastructure.bank.db.JdbcBankRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import javax.sql.DataSource

@Configuration
class BankConfiguration {

    @Bean
    fun bankRepository(jdbcTemplate: NamedParameterJdbcTemplate): BankRepository = JdbcBankRepository(jdbcTemplate)

    @Bean
    fun bankFacade(bankRepository: BankRepository): BankService = BankService(bankRepository)
}