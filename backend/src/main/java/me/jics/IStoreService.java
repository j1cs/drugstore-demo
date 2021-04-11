package me.jics;

import io.reactivex.Flowable;

public interface IStoreService {
    Flowable<Store> all();

    Flowable<Store> findByName(String name);

    Flowable<Store> findByBorough(String borough);

    Flowable<Store> findByBoroughAndName(String borough, String name);
}
