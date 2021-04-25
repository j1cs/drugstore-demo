package me.jics

import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.RxStreamingHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.uri.UriBuilder
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import io.reactivex.Flowable
import io.reactivex.Single
import spock.lang.AutoCleanup
import spock.lang.Ignore
import spock.lang.Shared
import spock.lang.Specification

import javax.inject.Inject
import java.time.LocalDate
import java.time.LocalTime

@MicronautTest
class StoreControllerSpec extends Specification {

    @Shared
    @AutoCleanup
    @Inject
    @Client("/store")
    RxHttpClient client

    @Inject
    IStoreService storeService

    void "Retrieve All Drugstores"() {
        given:
        Store mockStore = getMockStore()
        when:
        Flowable flowable = client.retrieve(HttpRequest.GET('/all'), Argument.listOf(Store))
        List<Store> store = flowable.firstElement().toSingle().blockingGet()
        then:
        storeService.all() >> getMockStores()
        store.find { it.id == mockStore.id }
    }

    void "Retrieve All Store's name"() {
        given:
        String mock = [ 'storeName' ]
        when:
        Flowable flowable = client.retrieve(HttpRequest.GET('/all/names'))
        String boroughName = flowable.blockingFirst()
        then:
        storeService.allNames() >> Flowable.just('storeName')
        boroughName == mock
    }

    void "Retrieve All Drugstores Filter By Borough Name"() {
        given:
        Store mockStore = getMockStore()
        String uri = UriBuilder.of('/borough').path(boroughName).build().toString()
        when:
        Flowable flowable = client.retrieve(HttpRequest.GET(uri), Argument.listOf(Store))
        List<Store> store = flowable.firstElement().toSingle().blockingGet()
        then:
        storeService.findByBorough(boroughName) >> getMockStores()
        store.find { it.id == id }
        where:
        boroughName                | id
        mockStore.getBoroughName() | mockStore.getId()

    }

    void "Retrieve All Drugstores Filter By Store Name"() {
        given:
        Store mockStore = getMockStore()
        String uri = UriBuilder.of('/name').path(name).build().toString()
        when:
        Flowable flowable = client.retrieve(HttpRequest.GET(uri), Argument.listOf(Store))
        List<Store> store = flowable.firstElement().toSingle().blockingGet()
        then:
        storeService.findByName(name) >> getMockStores()
        store.find { it.id == id }
        where:
        name                | id
        mockStore.getName() | mockStore.getId()
    }

    void "Retrieve All Drugstores Filter By Store Name And Borough Name"() {
        given:
        Store mockStore = getMockStore()
        String uri = UriBuilder.of(boroughName).path(name).build().toString()
        when:
        Flowable flowable = client.retrieve(HttpRequest.GET(uri), Argument.listOf(Store))
        List<Store> store = flowable.firstElement().toSingle().blockingGet()
        then:
        storeService.findByBoroughAndName(boroughName, name) >> getMockStores()
        store.find { it.id == id }
        where:
        boroughName                | name                | id
        mockStore.getBoroughName() | mockStore.getName() | mockStore.getId()
    }


    static def getMockStores() {
        return Single.just(getMockStore())
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
