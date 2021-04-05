package me.jics;

import io.micronaut.http.annotation.*;
import io.reactivex.Flowable;

import java.util.List;

@Controller("/store")
public class StoreController {
    private final StoreService service;

    public StoreController(StoreService service) {
        this.service = service;
    }

    @Get(uri = "/all")
    public Flowable<List<Store>> getAll() {
        return this.service.all();
    }
}