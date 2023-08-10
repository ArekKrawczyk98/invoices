package com.example.invoices

import com.example.invoices.domain.bank.BankAccount
import com.example.invoices.domain.bank.BankAccountId
import com.example.invoices.domain.bank.BankRepository
import com.example.invoices.domain.shared.NotFoundException

class BankRepositoryInMemory : BankRepository {

    private val database: HashMap<BankAccountId, BankAccount> = hashMapOf()

    override fun add(entity: BankAccount) {
        database[entity.id] = entity
    }

    override fun findAll(): List<BankAccount> {
        return database.values.toList()
    }

    override fun update(entity: BankAccount, id: BankAccountId) {
        database[id] = entity
    }

    override fun delete(id: BankAccountId) {
        database.remove(id)
    }

    override fun get(id: BankAccountId): BankAccount =
        database[id] ?: throw NotFoundException("Didnt find bank account with id $id")

}