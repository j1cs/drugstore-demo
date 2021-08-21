package me.jics;

import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.retry.annotation.Recoverable;
import io.reactivex.rxjava3.core.Flowable;

@Client("${services.minsal.url}")
@Recoverable(api = PharmacyOperations.class)
interface PharmacyClient extends PharmacyOperations {
    @Get(value = "${services.minsal.path}?${services.minsal.param}=${services.minsal.value}")
    Flowable<Pharmacy> retrieve();
}
