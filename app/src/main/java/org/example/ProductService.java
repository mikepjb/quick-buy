package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.Collections;

// Spring docs says @Service classes should contain business logic seperate from the server REST controller.
// I think the idiomatic way is to have a XController point to an XService (which can work with client REST servers
// as part of the business logic).
@Service
public class ProductService {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${product.api.base-url}")
    private String baseUrl;

    @Autowired
    public ProductService(RestTemplateBuilder restTemplateBuilder, ObjectMapper objectMapper) {
        this.restTemplate = restTemplateBuilder
                .setConnectTimeout(Duration.ofSeconds(30))
                .setReadTimeout(Duration.ofSeconds(30))
                .build();
        this.objectMapper = objectMapper;
    }

    // NOTES:
    // We could have avoiding throwing Exceptions for 4XX and 5XX but overriding hasError seems wild to me.
    //    DefaultResponseErrorHandler errorHandler = new DefaultResponseErrorHandler() {
    //        @Override
    //        public boolean hasError(HttpStatus statusCode) {
    //            return false; // Never treat responses as errors
    //        }
    //    };
    // I also really don't like that we have to handle bad json twice, once for 200 and the other for 404.
    // Overriding hasError would solve this.. but at what cost?
    // At the very least it's kinda cool you can locally override.

    public ProductResponse getProduct() {
        String url = baseUrl + "/product";

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    String.class
            );

            return objectMapper.readValue(response.getBody(), ProductResponse.class);

        } catch (HttpClientErrorException | HttpServerErrorException e) {
            // Handle 4xx and 5xx errors
            String errorResponseBody = e.getResponseBodyAsString();
            try {
                ErrorResponse errorResponse = objectMapper.readValue(errorResponseBody, ErrorResponse.class);
                throw new ProductApiException(
                        errorResponse.getError() != null ? errorResponse.getError() : e.getStatusText(),
                        errorResponse.getMessage() != null ? errorResponse.getMessage() : "API request failed with status " + e.getStatusCode()
                );
            } catch (JsonProcessingException jsonException) {
                // If we can't parse the error response, create a generic error
                throw new ProductApiException(
                        e.getStatusText(),
                        "API request failed with status " + e.getStatusCode()
                );
            }
        } catch (JsonProcessingException e) {
            // If we can't parse the error response, create a generic error
            throw new ProductApiException(
                    "200", // weirdly this case covers bad JSON on a 200 always
                    "API request failed with status 200"
            );
        }
    }
}