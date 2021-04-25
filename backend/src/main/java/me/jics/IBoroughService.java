package me.jics;

import io.reactivex.Flowable;

public interface IBoroughService {
    Flowable<String> all();
}
