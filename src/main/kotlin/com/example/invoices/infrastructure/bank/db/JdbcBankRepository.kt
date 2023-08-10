package com.example.invoices.infrastructure.bank.db

import com.example.invoices.domain.bank.BankAccount
import com.example.invoices.domain.bank.BankAccountId
import com.example.invoices.domain.bank.BankRepository
import com.example.invoices.domain.bank.CountryCode
import com.example.invoices.domain.shared.NotFoundException
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import java.sql.ResultSet

class JdbcBankRepository(private val jdbcTemplate: NamedParameterJdbcTemplate) : BankRepository {

    override fun add(entity: BankAccount) {
        val query = "INSERT INTO bank_accounts VALUES (:accountNumber, :name ,:countryCode);"
        jdbcTemplate.update(
            query,
            createMapWithAllParams(entity)
        )
    }

    override fun update(entity: BankAccount, id: BankAccountId) {
        val query = "UPDATE bank_accounts SET " +
                "number=:accountNumber, " +
                "name=:name, " +
                "country_code=:countryCode " +
                "WHERE number=:id;"
        jdbcTemplate.update(
            query,
            createMapWithAllParams(entity) + mapOf("id" to id.accountNumber)
        )
    }

    override fun delete(id: BankAccountId) {
        val query = "DELETE FROM bank_accounts WHERE number=:accountNumber"
        jdbcTemplate.update(
            query,
            mapOf(ACCOUNT_NUMBER to id.accountNumber)
        )
    }

    override fun get(id: BankAccountId): BankAccount {
        val query = "SELECT * FROM bank_accounts WHERE number=:accountNumber"
        return jdbcTemplate.queryForObject(
            query,
            mapOf(ACCOUNT_NUMBER to id.accountNumber),
            BankAccountRowMapper()
        ) ?: throw NotFoundException("Cannot find bank account with number ${id.display()}")
    }

    override fun findAll(): List<BankAccount> {
        val query = "SELECT * FROM bank_accounts"
        return jdbcTemplate.query(
            query,
            BankAccountRowMapper()
        )
    }

    private fun createMapWithAllParams(bankAccount: BankAccount): Map<String, String> {
        return mapOf(
            ACCOUNT_NUMBER to bankAccount.id.accountNumber,
            NAME to bankAccount.name,
            COUNTRY_CODE to bankAccount.countryCode.name
        )
    }

    companion object {
        const val ACCOUNT_NUMBER = "accountNumber"
        const val NAME = "name"
        const val COUNTRY_CODE = "countryCode"
    }

    class BankAccountRowMapper : RowMapper<BankAccount> {

        private val accountNumber = "number"
        private val name = "name"
        private val countryCode = "country_code"

        override fun mapRow(rs: ResultSet, rowNum: Int): BankAccount =
            BankAccount(
                BankAccountId(rs.getString(accountNumber)),
                rs.getString(name),
                CountryCode.valueOf(rs.getString(countryCode))
            )

    }
}