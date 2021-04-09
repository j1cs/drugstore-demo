package me.jics;

import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.retry.annotation.Retryable;
import io.reactivex.Flowable;

@Client("${services.minsal.url}")
public interface PharmacyClient {
    @Retryable(attempts = "5", delay = "5s")
    @Get(value = "${services.minsal.path}?${services.minsal.param}=${services.minsal.value}")
    Flowable<Pharmacy> retrieve();
}
