package com.example.gatewayfilter

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "stub")
data class UriConfiguration(val httpuri: String)