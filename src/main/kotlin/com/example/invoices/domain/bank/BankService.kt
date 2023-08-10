package com.example.invoices.domain.bank

class BankService(private val bankRepository: BankRepository) {

    fun add(bankAccount: BankAccount) = bankRepository.add(bankAccount)

    fun update(bankAccount: BankAccount, bankAccountId: BankAccountId) = bankRepository.update(bankAccount, bankAccountId)

    fun delete(bankAccountId: BankAccountId) = bankRepository.delete(bankAccountId)

    fun get(bankAccountId: BankAccountId) = bankRepository.get(bankAccountId)

    fun getAll() = bankRepository.findAll()

}
