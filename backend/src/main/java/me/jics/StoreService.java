package me.jics;

import io.reactivex.Flowable;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Singleton;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Singleton
public class StoreService {

    private final PharmacyClient pharmacyClient;

    public StoreService(PharmacyClient pharmacyClient) {
        this.pharmacyClient = pharmacyClient;
    }

    public Flowable<List<Store>> all() {
        Flowable<List<Pharmacy>> listFlowable = this.pharmacyClient.retrieve();
        return listFlowable
                .doOnError(throwable -> log.error(throwable.getLocalizedMessage()))
                .flatMap(pharmacies ->
                        Flowable.just(pharmacies.stream()
                                .map(pharmacy -> Store.builder().borough(pharmacy.getBoroughFk()).build())
                                .collect(Collectors.toList())
                        )
                );
    }

    /**
     * Closure to map Pharmacy to Store
     */
    private final Function<Pharmacy, Store> pharmacyStoreMapping = (Pharmacy pharmacy) -> Store.builder()
            .date(pharmacy.getDate())
            .id(pharmacy.getStoreId())
            .name(pharmacy.getStoreName())
            .communeName(pharmacy.getBoroughName())
            .location(pharmacy.getLocationName())
            .address(pharmacy.getStoreAddress())
            .openingHours(pharmacy.getOpeningHourOperation())
            .closingHours(pharmacy.getClosingHourOperation())
            .phone(pharmacy.getStorePhone())
            .latitude(pharmacy.getStoreLat())
            .longitude(pharmacy.getStoreLng())
            .openingDay(pharmacy.getOperationDay())
            .region(pharmacy.getRegionFk())
            .borough(pharmacy.getBoroughFk())
            .build();
}