package me.jics;

import io.reactivex.Flowable;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Singleton;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * Service layer abstraction from data.
 *
 * @author Juan Cuzmar
 * @version 1.0
 */
@Slf4j
@Singleton
public class StoreService {

    private final PharmacyClient pharmacyClient;

    /**
     * Inject dependencies.
     *
     * @param pharmacyClient Restful client abstraction layer
     */
    public StoreService(PharmacyClient pharmacyClient) {
        this.pharmacyClient = pharmacyClient;
    }

    /**
     * Service returns a list of pharmacies
     *
     * @return @{link List} a list of all stores
     */
    public Flowable<List<Store>> all() {
        Flowable<Pharmacy[]> flowable = this.pharmacyClient.retrieve();
        return flowable
                .switchMap(pharmacies ->
                        Flowable.just(Arrays.stream(pharmacies)
                                .map(pharmacyStoreMapping)
                                .collect(Collectors.toList())
                        )
                ).doOnError(throwable -> log.error(throwable.getLocalizedMessage()));
    }

    /**
     * Service filter by borough name and returns a list of drugstore
     *
     * @param borough string to filter
     * @return @{link List} a list of all stores filtered by commune name
     */
    public Flowable<List<Store>> findByBorough(String borough) {
        Flowable<Pharmacy[]> flowable = this.pharmacyClient.retrieve();
        return flowable
                .switchMap(pharmacies ->
                        Flowable.just(Arrays.stream(pharmacies)
                                .filter(pharmacy -> pharmacy.getBoroughName().equalsIgnoreCase(borough))
                                .map(pharmacyStoreMapping)
                                .collect(Collectors.toList())
                        )
                ).doOnError(throwable -> log.error(throwable.getLocalizedMessage()));
    }

    /**
     * Service filter by store name and returns a list of drugstore
     *
     * @param name string to filter
     * @return @{link List} a list of all stores filtered by store name
     */
    public Flowable<List<Store>> findByName(String name) {
        Flowable<Pharmacy[]> flowable = this.pharmacyClient.retrieve();
        return flowable
                .switchMap(pharmacies ->
                        Flowable.just(Arrays.stream(pharmacies)
                                .filter(pharmacy -> pharmacy.getStoreName().equalsIgnoreCase(name))
                                .map(pharmacyStoreMapping)
                                .collect(Collectors.toList())
                        )
                ).doOnError(throwable -> log.error(throwable.getLocalizedMessage()));
    }

    /**
     * Service filter by store and commune name that finally returns a list of drugstore
     *
     * @param borough string to filter
     * @param name    string to filter
     * @return @{link List} a list of all stores filtered by commune and store name
     */
    public Flowable<List<Store>> findByBoroughAndName(String borough, String name) {
        Flowable<Pharmacy[]> flowable = this.pharmacyClient.retrieve();
        return flowable
                .switchMap(pharmacies ->
                        Flowable.just(Arrays.stream(pharmacies)
                                .filter(pharmacy -> pharmacy.getStoreName().equalsIgnoreCase(name) && pharmacy.getBoroughName().equalsIgnoreCase(borough))
                                .map(pharmacyStoreMapping)
                                .collect(Collectors.toList())
                        )
                ).doOnError(throwable -> log.error(throwable.getLocalizedMessage()));
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