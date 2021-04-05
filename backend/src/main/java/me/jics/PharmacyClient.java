package me.jics;

import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.annotation.Client;
import io.reactivex.Flowable;

import java.util.List;

import static io.micronaut.http.MediaType.TEXT_HTML;
import static io.micronaut.http.MediaType.APPLICATION_JSON;

@Client("${services.minsal.url}")
public interface PharmacyClient {
    @Get("${services.minsal.path}?${services.minsal.param}=${services.minsal.value}")
    Flowable<List<Pharmacy>> retrieve();
}
