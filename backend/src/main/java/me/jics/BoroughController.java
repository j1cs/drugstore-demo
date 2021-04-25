package me.jics;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.reactivex.Flowable;

@Controller("/borough")
public class BoroughController {
    private final IBoroughService service;

    public BoroughController(IBoroughService service) {
        this.service = service;
    }

    @Get(uri = "/all")
    public Flowable<String> index() {
        return this.service.all();
    }
}