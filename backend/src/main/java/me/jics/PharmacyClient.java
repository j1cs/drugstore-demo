package me.jics;

import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.annotation.Client;
import io.reactivex.Flowable;

@Client("${services.minsal.url}")
public interface PharmacyClient {
    @Get(value = "${services.minsal.path}?${services.minsal.param}=${services.minsal.value}")
    Flowable<Pharmacy[]> retrieve();
}
