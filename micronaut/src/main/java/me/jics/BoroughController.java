package me.jics;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.reactivex.rxjava3.core.Single;

import java.util.List;

@Controller("/borough")
public class BoroughController {
    private final IBoroughService service;

    public BoroughController(IBoroughService service) {
        this.service = service;
    }

    @Get(uri = "/all")
    public Single<List<String>> boroughs() {
        return this.service.all();
    }
}