package me.jics;

import io.reactivex.Flowable;

public interface PharmacyOperations {
    Flowable<Pharmacy> retrieve();
}
