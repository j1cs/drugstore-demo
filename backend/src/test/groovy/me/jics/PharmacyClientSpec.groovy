package me.jics

import io.micronaut.context.ApplicationContext
import io.micronaut.context.annotation.Requires
import io.micronaut.core.io.socket.SocketUtils
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.runtime.server.EmbeddedServer
import io.reactivex.Flowable
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDate
import java.time.LocalTime

class PharmacyClientSpec extends Specification {

    @Shared
    int port = SocketUtils.findAvailableTcpPort()

    @AutoCleanup
    @Shared
    EmbeddedServer server = ApplicationContext.run(EmbeddedServer, [
            'services.minsal.url'  : "http://localhost:$port",
            'services.minsal.path' : '/pharmacies',
            'spec.name'            : 'mockPharmacy',
            'micronaut.server.port': port
    ]) as EmbeddedServer

    @Shared
    @Subject
    PharmacyClient client = server.applicationContext.getBean(PharmacyClient);

    void "Fetch all Pharmacies"() {
        when:
        Pharmacy pharmacy = client.retrieve().blockingFirst()
        then:
        pharmacy.getStoreId() == '1'
    }


    @Requires(property = 'spec.name', value = 'mockPharmacy')
    @Controller("/pharmacies")
    static class MockPharmacy {
        @Get
        Flowable<Pharmacy> index() {
            Flowable.just(Pharmacy.builder()
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
                    .build())
        }
    }

}
