package com.example.gatewayfilter

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.RestController

@RestController
@SpringBootApplication
class GatewayfilterApplication

fun main(args: Array<String>) {
	runApplication<GatewayfilterApplication>(*args)
}
