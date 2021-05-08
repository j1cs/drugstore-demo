package me.jics;

import io.micronaut.http.annotation.Get;
import io.reactivex.Flowable;

public interface PharmacyOperations {
    @Get(value = "${services.minsal.path}?${services.minsal.param}=${services.minsal.value}")
    Flowable<Pharmacy> retrieve();
}
