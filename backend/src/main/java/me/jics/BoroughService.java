package me.jics;

import io.micronaut.cache.annotation.Cacheable;
import io.micronaut.core.util.StringUtils;
import io.reactivex.Flowable;
import io.reactivex.Single;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Singleton;
import java.util.List;

/**
 * Service layer abstraction from data.
 *
 * @author Juan Cuzmar
 * @version 1.0
 */
@Slf4j
@Singleton
public class BoroughService implements IBoroughService {

    private final PharmacyClient pharmacyClient;

    /**
     * Inject dependencies.
     *
     * @param pharmacyClient Restful client abstraction layer
     */
    public BoroughService(PharmacyClient pharmacyClient) {
        this.pharmacyClient = pharmacyClient;
    }

    /**
     * Service returns an array of store's names
     *
     * @return Flowable an array of store's names
     */
    @Override
    @Cacheable("borough-all-names")
    public Single<List<String>> all() {
        Flowable<Pharmacy> flowable = this.pharmacyClient.retrieve();
        return flowable
                .map(pharmacy -> StringUtils.capitalize(pharmacy.getBoroughName().toLowerCase()))
                .distinct(String::toString)
                .toList()
                .doFinally(() -> log.info("List of borough names"))
                .doOnError(throwable -> log.error(throwable.getLocalizedMessage()));
    }
}