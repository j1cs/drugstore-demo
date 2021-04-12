package me.jics


import io.micronaut.context.ApplicationContext
import io.micronaut.context.annotation.Requires
import io.micronaut.core.io.socket.SocketUtils
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.runtime.server.EmbeddedServer
import io.reactivex.Flowable
import spock.lang.Specification

import java.time.LocalDate
import java.time.LocalTime

class PharmacyClientSpec extends Specification {


    void "Fetch all Pharmacies"() {
        given:
        int mockHttpServerPort = SocketUtils.findAvailableTcpPort()
        EmbeddedServer mockHttpServer = ApplicationContext.run(EmbeddedServer, [
                'services.minsal.url'  : "http://localhost:$mockHttpServerPort",
                'services.minsal.path' : '/pharmacies',
                'spec.name'            : 'mockPharmacy',
                'micronaut.server.port': mockHttpServerPort
        ])
        PharmacyClient client = mockHttpServer.applicationContext.getBean(PharmacyClient)
        when:
        Pharmacy pharmacy = client.retrieve().blockingFirst()
        then:
        pharmacy.getStoreId() == '1'
        cleanup:
        mockHttpServer.close()
    }


    @Requires(property = 'spec.name', value = 'mockPharmacy')
    @Controller
    static class MockPharmacy {
        @Get("/pharmacies")
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
