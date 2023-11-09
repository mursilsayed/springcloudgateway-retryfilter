package com.example.gatewayfilter

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@EnableConfigurationProperties(UriConfiguration::class)
@Configuration
class RoutesConfiguration {
    @Bean
    fun myRoutes(builder: RouteLocatorBuilder, uriConfiguration: UriConfiguration): RouteLocator {
        val httpUri = uriConfiguration.httpuri
        return builder.routes()
            .route("test-route") {
                it.path("/get")
                    .filters {
                        it.addRequestHeader("Hello", "World")
                    }
                    .uri(httpUri)
            }
            .route("retry-route") {
                it.path("/retry")
                    .filters {
                        it.retry(5)
                    }
                    .uri(httpUri)
            }
            .build()
    }
}