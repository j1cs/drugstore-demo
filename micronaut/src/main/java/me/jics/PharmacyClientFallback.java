package me.jics;

import io.micronaut.retry.annotation.Fallback;
import io.reactivex.Flowable;

import java.time.LocalDate;
import java.time.LocalTime;

@Fallback
public class PharmacyClientFallback implements PharmacyOperations {
    @Override
    public Flowable<Pharmacy> retrieve() {
        return Flowable.just(Pharmacy.builder()
                .date(LocalDate.now())
                .storeId("1")
                .storeName("store")
                .boroughName("borough")
                .locationName("location")
                .storeAddress("address")
                .openingHourOperation(LocalTime.now())
                .closingHourOperation(LocalTime.now())
                .storePhone("+56999999999")
                .storeLat(0.0)
                .storeLng(0.0)
                .openingHourOperation(LocalTime.now())
                .regionFk(1)
                .boroughFk(1)
                .build());
    }
}
