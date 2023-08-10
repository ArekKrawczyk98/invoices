package com.example.invoices.application.shared

import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.stereotype.Component
import java.util.*

@Component
class LanguageUtils(
    private val messageSource: MessageSource
) {

    fun getLocalizedMessage(key: String) =
        messageSource.getMessage(key, null, getLocale())

    private fun getLocale(): Locale =
        LocaleContextHolder.getLocale()
}