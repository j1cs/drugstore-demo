package me.jics;


import io.reactivex.rxjava3.core.Single;

import java.util.List;

public interface IStoreService {
    Single<List<Store>> all();

    Single<List<String>> allNames();

    Single<List<Store>> findByName(String name);

    Single<List<Store>> findByBorough(String borough);

    Single<List<Store>> findByBoroughAndName(String borough, String name);
}
