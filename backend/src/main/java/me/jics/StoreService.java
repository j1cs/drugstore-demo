package me.jics;

import io.micronaut.cache.annotation.Cacheable;
import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;

import javax.inject.Singleton;


/**
 * Service layer abstraction from data.
 *
 * @author Juan Cuzmar
 * @version 1.0
 */
@Slf4j
@Singleton
public class StoreService implements IStoreService {

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
    @Override
    @Cacheable("all")
    public Flowable<Store> all() {
        log.info("Entering to StoreService.all");
        Flowable<Pharmacy> flowable = this.pharmacyClient.retrieve();
        log.info("Got pharmacies from the client");
        return flowable
                .switchMap(pharmacyPublisherFunction)
                .doFinally(() -> log.info("Pharmacy to Store finished"))
                .doOnComplete(() -> log.info("Pharmacy to Store complete"))
                .doOnError(throwable -> log.error(throwable.getLocalizedMessage()));

    }

    /**
     * Service filter by borough name and returns a list of drugstore
     *
     * @param borough string to filter
     * @return @{link List} a list of all stores filtered by commune name
     */
    @Override
    @Cacheable(value = "find-by-borough", parameters = "borough")
    public Flowable<Store> findByBorough(String borough) {
        log.info("Entering to StoreService.findByBorough with borough:{}", borough);
        Flowable<Pharmacy> flowable = this.pharmacyClient.retrieve();
        log.info("Got pharmacies from the client");
        return flowable
                .filter(pharmacy -> pharmacy.getBoroughName().equalsIgnoreCase(borough))
                .switchMap(pharmacyPublisherFunction)
                .doFinally(() -> log.info("Pharmacy to Store finished filtered by borough"))
                .doOnComplete(() -> log.info("Pharmacy to Store complete filtered by borough"))
                .doOnError(throwable -> log.error(throwable.getLocalizedMessage()));
    }

    /**
     * Service filter by store name and returns a list of drugstore
     *
     * @param name string to filter
     * @return @{link List} a list of all stores filtered by store name
     */
    @Override
    @Cacheable(value = "find-by-name", parameters = "name")
    public Flowable<Store> findByName(String name) {
        log.info("Entering to StoreService.findByName with name:{}", name);
        Flowable<Pharmacy> flowable = this.pharmacyClient.retrieve();
        log.info("Got pharmacies from the client");
        return flowable
                .filter(pharmacy -> pharmacy.getStoreName().equalsIgnoreCase(name))
                .switchMap(pharmacyPublisherFunction)
                .doFinally(() -> log.info("Pharmacy to Store finished filtered by borough"))
                .doOnComplete(() -> log.info("Pharmacy to Store complete filtered by borough"))
                .doOnError(throwable -> log.error(throwable.getLocalizedMessage()));
    }

    /**
     * Service filter by store and commune name that finally returns a list of drugstore
     *
     * @param borough string to filter
     * @param name    string to filter
     * @return @{link List} a list of all stores filtered by commune and store name
     */
    @Override
    @Cacheable(value = "find-by-borough-and-Name", parameters = {"borough", "name"})
    public Flowable<Store> findByBoroughAndName(String borough, String name) {
        log.info("Entering to StoreService.findByBoroughAndName with borough:{} and name:{}", borough, name);
        Flowable<Pharmacy> flowable = this.pharmacyClient.retrieve();
        log.info("Got pharmacies from the client");
        return flowable
                .filter(pharmacy -> pharmacy.getStoreName().equalsIgnoreCase(name) && pharmacy.getBoroughName().equalsIgnoreCase(borough))
                .switchMap(pharmacyPublisherFunction)
                .doFinally(() -> log.info("Pharmacy to Store finished filtered by borough and name"))
                .doOnComplete(() -> log.info("Pharmacy to Store complete filtered by borough and name"))
                .doOnError(throwable -> log.error(throwable.getLocalizedMessage()));
    }

    /**
     * Closure to map Pharmacy to Store
     */
    private final Function<Pharmacy, Publisher<Store>> pharmacyPublisherFunction = (Pharmacy pharmacy) -> Flowable.just(Store.builder()
            .date(pharmacy.getDate())
            .id(pharmacy.getStoreId())
            .name(pharmacy.getStoreName())
            .boroughName(pharmacy.getBoroughName())
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
            .build());
}