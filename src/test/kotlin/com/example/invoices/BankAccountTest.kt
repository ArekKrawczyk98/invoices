package com.example.invoices

import com.example.invoices.domain.bank.BankAccountId
import com.example.invoices.domain.shared.ValidationException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue

class BankAccountTest {

    @Test
    fun shouldThrowExceptionWhenCreatingBankAccountNotWith26Digits() {
        assertThrows<ValidationException> {
            BankAccountId("124066661029568620212255")
        }.also {
            assertTrue { it.message == "Account number must have 26 digits" }
        }
    }

    @Test
    fun shouldDisplayAccountNumberInEasyToReadFormat() {
        val bankId = BankAccountId("12406666102956862021225512")
        assertEquals("12 4066 6610 2956 8620 2122 5512", bankId.display())
    }

}