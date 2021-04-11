package me.jics

import io.micronaut.context.ApplicationContext
import io.micronaut.context.annotation.Requires
import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.QueryValue
import io.micronaut.http.client.HttpClient
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import io.reactivex.Flowable
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

import javax.inject.Inject

class PharmacyClientSpec extends Specification {
    @AutoCleanup
    @Shared
    EmbeddedServer embeddedServer = ApplicationContext.run(EmbeddedServer, ['pharmacy.mock': 'mockPharmacyClient'])

    @AutoCleanup
    @Shared
    ApplicationContext applicationContext = embeddedServer.applicationContext

    @AutoCleanup
    @Shared
    HttpClient httpClient = applicationContext.createBean(HttpClient, embeddedServer.URL)

    void "Fetch all Pharmacies"() {
        given:
        HttpRequest request = HttpRequest.GET('/pharmacies')
        when:
        HttpResponse<Pharmacy> response = httpClient.toBlocking().exchange(request, Pharmacy)
        then:
        response.code() == HttpStatus.OK.getCode()
    }

    @Controller("/")
    @Requires(property = 'pharmacy.mock', value = 'mockPharmacyClient')
    static class PharmacyMock {

        @Get
        String retrieve() {
            '[{"fecha":"11-04-2021","local_id":"753","local_nombre":"AHUMADA","comuna_nombre":"BUIN","localidad_nombre":"BUIN","local_direccion":"SAN MARTIN 174","funcionamiento_hora_apertura":"09:00 hrs.","funcionamiento_hora_cierre":"22:00 hrs.","local_telefono":"+560226313086","local_lat":"-33.732","local_lng":"-70.735941","funcionamiento_dia":"domingo","fk_region":"7","fk_comuna":"83"}]'
        }

    }
}
