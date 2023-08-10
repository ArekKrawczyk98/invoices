package com.example.invoices.infrastructure.bank.http

import com.example.invoices.domain.bank.BankAccount
import com.example.invoices.domain.bank.BankAccountId
import com.example.invoices.domain.bank.BankService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/bank-account")
@RestController
class BankEndpoint(
    private val bankService: BankService
) {

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    fun add(@RequestBody bankAccount: BankAccount) = bankService.add(bankAccount)

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    fun put(@RequestBody bankAccount: BankAccount, @PathVariable("id") number: String) = bankService.update(bankAccount, BankAccountId(number))

    @GetMapping("/{id}")
    fun get(@PathVariable("id") number: String) = bankService.get(BankAccountId(number))

    @GetMapping
    fun getAll() = bankService.getAll()

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    fun delete(@PathVariable("id") number: String) = bankService.delete(BankAccountId(number))

}