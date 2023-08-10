package com.example.invoices.domain.invoice

data class Business(
    val name: String,
    val address: BusinessAddress,
    val nip: String
)

data class BusinessAddress(
    val location: String,
    val postCode: String,
    val generalLocation: String
)


