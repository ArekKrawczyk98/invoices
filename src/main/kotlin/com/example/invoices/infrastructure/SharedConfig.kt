package com.example.invoices.infrastructure

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import javax.sql.DataSource

@Configuration
class SharedConfig(
    val dataSource: DataSource
) {

    @Bean
    fun namedParameterJdbcTemplate(): NamedParameterJdbcTemplate = NamedParameterJdbcTemplate(JdbcTemplate(dataSource))
}