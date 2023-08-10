package com.example.invoices.domain.item

import java.math.BigDecimal

data class Item(val name: String, val quantity: Int, val netPrice: BigDecimal, val vat: Double)
