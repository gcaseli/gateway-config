package br.com.gateway;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import org.junit.ClassRule;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
class GatewayApplicationTests {

	@Autowired
	private WebTestClient webClient;

	@ClassRule
	public static WireMockClassRule wiremock = new WireMockClassRule(wireMockConfig().port(9904));

	@Test
	void contextLoads() throws Exception {
		//Stubs
		stubFor(WireMock.get(urlEqualTo("/get"))
				.willReturn(aResponse()
						.withBody("{\"headers\":{\"Hello\":\"Gateway\"}}")
						.withHeader("Content-Type", "application/json")));

		webClient
				.get().uri("/get")
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.jsonPath("$.headers.Hello").isEqualTo("Gateway");

	}

}
