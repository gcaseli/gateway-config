package br.com.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;

@Configuration
public class LoggingGlobalFiltersConfigurations {

  final Logger logger =
      LoggerFactory.getLogger(LoggingGlobalFiltersConfigurations.class);

  @Bean
  public GlobalFilter postGlobalFilter() {
    return (exchange, chain) -> chain.filter(exchange)
        .then(Mono.fromRunnable(() -> {
          ServerHttpRequest request = exchange.getRequest();
          ServerHttpResponse response = exchange.getResponse();
          logger.info(String.format("request.getPath() : %s",  request.getPath()));
          logger.info(String.format("request.getLocalAddress() : %s",  request.getLocalAddress()));
          HttpStatus statusCode = response.getStatusCode();
          if(statusCode != null){
            logger.info(statusCode.is2xxSuccessful() ? "It worked": "It did not work");
          }
          logger.info("Global Post Filter executed, send to new relic");
        }));
  }

}
