package me.jics;

import io.reactivex.Single;

import java.util.List;

public interface IBoroughService {
    Single<List<String>> all();
}
