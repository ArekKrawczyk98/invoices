package com.example.invoices.infrastructure

import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ResourceBundleMessageSource
import org.springframework.web.servlet.LocaleResolver
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver
import java.util.*

@Configuration
class LocaleConfig {

    @Bean
    fun localeResolver(): LocaleResolver {
        val resolver = AcceptHeaderLocaleResolver()
        resolver.supportedLocales = listOf(Locale.ENGLISH, Locale("pl"))
        resolver.setDefaultLocale(Locale.forLanguageTag("pl"))
        return resolver
    }

    @Bean
    fun messageSource(): MessageSource {
        val bundleMessageSource = ResourceBundleMessageSource()
        bundleMessageSource.setBasename("i18n/messages")
        bundleMessageSource.setDefaultEncoding("UTF-8")
        return bundleMessageSource
    }
}