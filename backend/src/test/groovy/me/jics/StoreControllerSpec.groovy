package me.jics


import io.micronaut.http.HttpRequest
import io.micronaut.http.client.RxStreamingHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.uri.UriBuilder
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import io.reactivex.Flowable
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

import javax.inject.Inject
import java.time.LocalDate
import java.time.LocalTime

@MicronautTest
class StoreControllerSpec extends Specification {

    @Shared
    @Inject
    EmbeddedServer embeddedServer

    @Shared
    @AutoCleanup
    @Inject
    @Client("/store")
    RxStreamingHttpClient client

    @Inject
    IStoreService storeService

    void "Retrieve All Drugstores"() {
        given:
        Store mockStore = getMockStore()
        when:
        Flowable<Store> storeFlowable = client.jsonStream(HttpRequest.GET('/all'), Store)
        def store = storeFlowable.firstElement().blockingGet()
        then:
        storeService.all() >> getMockStores()
        store.find { it.id = mockStore.getId() }
    }

    void "Retrieve All Drugstores Filter By Borough Name"() {
        given:
        Store mockStore = getMockStore()
        String uri = UriBuilder.of('/borough').path(boroughName).build().toString()
        when:
        Flowable<Store> storeFlowable = client.jsonStream(HttpRequest.GET(uri), Store)
        def store = storeFlowable.firstElement().blockingGet()
        then:
        storeService.findByBorough(mockStore.getBoroughName()) >> getMockStores()
        store.getId() == id
        where:
        boroughName                | id
        mockStore.getBoroughName() | mockStore.getId()

    }

    void "Retrieve All Drugstores Filter By Store Name"() {
        given:
        Store mockStore = getMockStore()
        String uri = UriBuilder.of('/name').path(name).build().toString()
        when:
        Flowable<Store> storeFlowable = client.jsonStream(HttpRequest.GET(uri), Store)
        def store = storeFlowable.firstElement().blockingGet()
        then:
        storeService.findByName(mockStore.getName()) >> getMockStores()
        store.getId() == id
        where:
        name                | id
        mockStore.getName() | mockStore.getId()
    }

    void "Retrieve All Drugstores Filter By Store Name And Borough Name"() {
        given:
        Store mockStore = getMockStore()
        String uri = UriBuilder.of(boroughName).path(name).build().toString()
        when:
        Flowable<Store> storeFlowable = client.jsonStream(HttpRequest.GET(uri), Store)
        def store = storeFlowable.firstElement().blockingGet()
        then:
        storeService.findByBoroughAndName(mockStore.getBoroughName(), mockStore.getName()) >> getMockStores()
        store.getId() == id
        where:
        boroughName                | name                | id
        mockStore.getBoroughName() | mockStore.getName() | mockStore.getId()
    }


    static def getMockStores() {
        return Flowable.just(getMockStore())
    }

    static def getMockStore() {
        return Store.builder()
                .date(LocalDate.now())
                .id("1")
                .name("store")
                .boroughName("boroughName")
                .location("location")
                .address("address")
                .openingHours(LocalTime.now())
                .closingHours(LocalTime.now())
                .phone("+56999999999")
                .latitude(0.0)
                .longitude(0.0)
                .openingDay("opening day")
                .region(1)
                .borough(1)
                .build()
    }

    @MockBean(StoreService)
    IStoreService storeService() {
        Mock(IStoreService)
    }
}
