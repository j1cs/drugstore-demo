package me.jics

import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import io.reactivex.Flowable
import spock.lang.Specification

import javax.inject.Inject
import java.time.LocalDate
import java.time.LocalTime

@MicronautTest
class StoreServiceSpec extends Specification {

    @Inject
    IStoreService service

    @Inject
    PharmacyClient client

    void "Retrieve All Pharmacies"() {
        given:
        Pharmacy mockPharmacy = getMockPharmacy()
        when:
        Flowable<Store> storeFlowable = service.all()
        Store store = storeFlowable.firstElement().blockingGet()
        then:
        client.retrieve() >> getMockPharmacies()
        store.find { it.id == mockPharmacy.getStoreId() }
    }

    void "Retrieve All Pharmacies Filter By Borough Name"() {
        given:
        Pharmacy mockPharmacy = getMockPharmacy()
        when:
        Flowable<Store> storeFlowable = service.findByBorough(boroughName)
        Store store = storeFlowable.firstElement().blockingGet()
        then:
        client.retrieve() >> getMockPharmacies()
        store.getId() == id
        where:
        boroughName                   | id
        mockPharmacy.getBoroughName() | mockPharmacy.getStoreId()
    }

    void "Retrieve All Pharmacies Filter By Pharmacy Name"() {
        given:
        Pharmacy mockPharmacy = getMockPharmacy()
        when:
        Flowable<Store> storeFlowable = service.findByName(name)
        Store store = storeFlowable.firstElement().blockingGet()
        then:
        client.retrieve() >> getMockPharmacies()
        store.getId() == id
        where:
        name                        | id
        mockPharmacy.getStoreName() | mockPharmacy.getStoreId()
    }


    void "Retrieve All Pharmacies Filter By Pharmacy Name And Borough Name"() {
        given:
        Pharmacy mockPharmacy = getMockPharmacy()
        when:
        Flowable<Store> storeFlowable = service.findByBoroughAndName(boroughName, name)
        Store store = storeFlowable.firstElement().blockingGet()
        then:
        client.retrieve() >> getMockPharmacies()
        store.getId() == id
        where:
        boroughName                   | name                        | id
        mockPharmacy.getBoroughName() | mockPharmacy.getStoreName() | mockPharmacy.getStoreId()
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
    PharmacyClient pharmacyClient() {
        Mock(PharmacyClient)
    }
}
