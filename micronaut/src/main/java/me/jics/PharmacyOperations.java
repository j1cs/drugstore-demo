package me.jics;

import io.reactivex.rxjava3.core.Flowable;

public interface PharmacyOperations {
    Flowable<Pharmacy> retrieve();
}
