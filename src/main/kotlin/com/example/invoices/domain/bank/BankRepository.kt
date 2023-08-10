package com.example.invoices.domain.bank

interface BankRepository {
    fun update(entity: BankAccount, id: BankAccountId)
    fun add(entity: BankAccount)
    fun delete(id: BankAccountId)
    fun get(id: BankAccountId): BankAccount
    fun findAll(): List<BankAccount>
}
