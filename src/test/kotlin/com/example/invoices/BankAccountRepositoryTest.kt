package com.example.invoices

import com.example.invoices.domain.bank.BankAccount
import com.example.invoices.domain.bank.BankAccountId
import com.example.invoices.domain.bank.BankRepository
import com.example.invoices.domain.bank.CountryCode
import com.example.invoices.domain.bank.CountryCode.*
import com.example.invoices.domain.shared.NotFoundException
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows


class BankAccountRepositoryTest {

    private lateinit var repository: BankRepository

    @BeforeEach
    fun setup() {
        repository = BankRepositoryInMemory()
    }

    @Test
    fun shouldAddBankAccount() {
        val bankId = BankAccountId("12406666102956862021225512")
        val account = BankAccount(bankId, "name", PL)

        repository.add(account)

        assertTrue { repository.findAll().size == 1}
    }

    @Test
    fun shouldUpdateBankAccount() {
        val bankId = BankAccountId("12406666102956862021225512")
        val account = BankAccount(bankId, "name", PL)
        repository.add(account)

        val updatedName = "updated name"
        val updatedAccount = BankAccount(bankId, updatedName, PL)
        repository.update(updatedAccount, bankId)

        assertTrue { repository.get(bankId).name == updatedName}
        assertTrue { repository.findAll().size == 1}
    }

    @Test
    fun shouldDeleteBankAccount() {
        val bankId = BankAccountId("12406666102956862021225512")
        val account = BankAccount(bankId, "name", PL)
        repository.add(account)

        repository.delete(bankId)
        assertTrue { repository.findAll().isEmpty() }
        assertThrows<NotFoundException> { repository.get(bankId) }
    }

}