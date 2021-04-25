package me.jics;

import io.reactivex.Flowable;
import io.reactivex.Single;

import java.util.List;

public interface IStoreService {
    Single<List<Store>> all();

    Flowable<String> allNames();

    Single<List<Store>> findByName(String name);

    Single<List<Store>> findByBorough(String borough);

    Single<List<Store>> findByBoroughAndName(String borough, String name);
}
