package me.jics

import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import spock.lang.Specification

import jakarta.inject.Inject
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
        Single<List<Store>> listSingle = service.all()
        List<Store> store = listSingle.blockingGet()
        then:
        client.retrieve() >> getMockPharmacies()
        store.find { it.id == mockPharmacy.getStoreId() }
    }

    void "Retrieve All Pharmacies Filter By Borough Name"() {
        given:
        Pharmacy mockPharmacy = getMockPharmacy()
        when:
        Single<List<Store>> listSingle = service.findByBorough(boroughName)
        List<Store> store = listSingle.blockingGet()
        then:
        client.retrieve() >> getMockPharmacies()
        store.findAll { it.id == id && it.boroughName == boroughName }
        where:
        boroughName                   | id
        mockPharmacy.getBoroughName() | mockPharmacy.getStoreId()
    }

    void "Retrieve All Pharmacies Filter By Pharmacy Name"() {
        given:
        Pharmacy mockPharmacy = getMockPharmacy()
        when:
        Single<List<Store>> listSingle = service.findByName(name)
        List<Store> store = listSingle.blockingGet()
        then:
        client.retrieve() >> getMockPharmacies()
        store.findAll { it.id == id && it.name == name }
        where:
        name                        | id
        mockPharmacy.getStoreName() | mockPharmacy.getStoreId()
    }


    void "Retrieve All Pharmacies Filter By Pharmacy Name And Borough Name"() {
        given:
        Pharmacy mockPharmacy = getMockPharmacy()
        when:
        Single<List<Store>> listSingle = service.findByBoroughAndName(boroughName, name)
        List<Store> store = listSingle.blockingGet()
        then:
        client.retrieve() >> getMockPharmacies()
        store.findAll { it.id == id && it.boroughName == boroughName && it.name == name }
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
