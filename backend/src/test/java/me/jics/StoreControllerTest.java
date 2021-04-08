package me.jics;

import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@MicronautTest
public class StoreControllerTest {

    @Inject
    @Client("/store")
    RxHttpClient client;

    @Test
    public void testGetAll() throws Exception {
        HttpRequest<?> request = HttpRequest.GET("/all");
        HttpResponse<?> response = client.toBlocking().exchange(request, Argument.of(ArrayList.class));
        log.info(String.valueOf(response.getBody()));
        assertEquals(response.code(), HttpStatus.OK.getCode());
        assertEquals(response.status(), HttpStatus.OK);
        assertNotNull(response.getBody());
    }
}
