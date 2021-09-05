package me.jics

import groovy.util.logging.Slf4j
import io.micronaut.core.util.StringUtils
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import spock.lang.Specification

import jakarta.inject.Inject
import java.time.LocalDate
import java.time.LocalTime

@Slf4j
@MicronautTest
class BoroughServiceSpec extends Specification {

    @Inject
    IBoroughService service

    @Inject
    PharmacyOperations client

    void "Retrieve All Pharmacies to get all Boroughs"() {
        given:
        Pharmacy mockPharmacy = getMockPharmacy()
        when:
        Single<List<String>> listSingle = service.all()
        List<String> boroughName = listSingle.blockingGet()
        then:
        client.retrieve() >> getMockPharmacies()
        boroughName.find { it.toString() == StringUtils.capitalize(mockPharmacy.getBoroughName()) }
    }

    static def getMockPharmacies() {
        return Flowable.just(getMockPharmacy())
    }

    static def getMockPharmacy() {
        return Pharmacy.builder()
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
                .build()
    }

    @MockBean(PharmacyClient)
    PharmacyOperations pharmacyClient() {
        Mock(PharmacyOperations)
    }
}
