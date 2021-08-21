package me.jics;

import io.reactivex.rxjava3.core.Single;

import java.util.List;

public interface IBoroughService {
    Single<List<String>> all();
}
