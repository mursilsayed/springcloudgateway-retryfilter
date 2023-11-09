package com.example.gatewayfilter

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.CountMatchingStrategy
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.client.WireMock.aResponse
import com.github.tomakehurst.wiremock.client.WireMock.get
import com.github.tomakehurst.wiremock.client.WireMock.stubFor
import com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock
import org.springframework.test.web.reactive.server.WebTestClient


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
class GatewayfilterApplicationTests {

    @Autowired
    private lateinit var webClient: WebTestClient

    @Autowired
    private lateinit var wireMockServer: WireMockServer

    @Test
    fun testBasicRouting() {
        //Stubs
        stubFor(
            get(urlEqualTo("/get"))
                .willReturn(
                    aResponse()
                        .withBody("{\"headers\":{\"Hello\":\"World\"}}")
                        .withHeader("Content-Type", "application/json")
                )
        )

        webClient
            .get().uri("/get")
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.headers.Hello").isEqualTo("World")
    }

    @Test
    fun testRoutingWithRetryFilter() {
        //Stubs
        stubFor(
            get(urlEqualTo("/retry"))
                .willReturn(
                    ResponseDefinitionBuilder().withBody("Helloworld!")
                        .withStatus(429)
                )
        )

        webClient
            .get().uri("/retry")
            .exchange()
            .expectStatus().isEqualTo(429)

        wireMockServer.verify(
            CountMatchingStrategy(CountMatchingStrategy.GREATER_THAN, 1),
            WireMock.getRequestedFor(urlEqualTo("/retry"))
        )
    }
}
