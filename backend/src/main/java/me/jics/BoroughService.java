package me.jics;

import io.micronaut.core.util.StringUtils;
import io.reactivex.Flowable;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Singleton;
import java.util.Locale;

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

    @Override
    public Flowable<String> all() {
        Flowable<Pharmacy> flowable = this.pharmacyClient.retrieve();
        return Flowable.fromPublisher(
                flowable.distinct(Pharmacy::getBoroughName)
                .map(pharmacy -> StringUtils.capitalize(pharmacy.getBoroughName().toLowerCase()))
        );
    }
}