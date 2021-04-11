package me.jics

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import io.micronaut.context.ApplicationContext
import io.micronaut.context.annotation.Requires
import io.micronaut.http.HttpRequest
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.client.RxStreamingHttpClient
import io.micronaut.runtime.server.EmbeddedServer
import io.reactivex.Flowable
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

import java.time.LocalDate
import java.time.LocalTime

class PharmacyClientSpec extends Specification {
    @AutoCleanup
    @Shared
    EmbeddedServer embeddedServer = ApplicationContext.run(EmbeddedServer, ['pharmacy.mock': 'mockPharmacyClient'])

    @AutoCleanup
    @Shared
    ApplicationContext applicationContext = embeddedServer.applicationContext

    @AutoCleanup
    @Shared
    RxStreamingHttpClient client = applicationContext.createBean(RxStreamingHttpClient, embeddedServer.URL)

    void "Fetch all Pharmacies"() {
        given:
        HttpRequest request = HttpRequest.GET('/pharmacies')
        when:
        Flowable<Pharmacy> pharmacyFlowable = client.jsonStream(request, Pharmacy)
        Pharmacy pharmacy = pharmacyFlowable.firstElement().blockingGet()
        then:
        pharmacy.find { it.storeId == PharmacyMock.getMockPharmacy().getStoreId() }
    }

    @Controller("/pharmacies")
    @Requires(property = 'pharmacy.mock', value = 'mockPharmacyClient')
    static class PharmacyMock {

        static Pharmacy getMockPharmacy() {
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

        @Get
        String retrieve() {
            ObjectMapper objectMapper = new ObjectMapper()
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.writeValueAsString(List.of(getMockPharmacy()))
        }

    }
}
