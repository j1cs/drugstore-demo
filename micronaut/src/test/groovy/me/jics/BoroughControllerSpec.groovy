package me.jics

import groovy.util.logging.Slf4j
import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.annotation.Client
import io.micronaut.rxjava3.http.client.Rx3HttpClient
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

import jakarta.inject.Inject

@Slf4j
@MicronautTest
class BoroughControllerSpec extends Specification {

    @Shared
    @AutoCleanup
    @Inject
    @Client("/borough")
    Rx3HttpClient client

    @Inject
    IBoroughService boroughService

    void "Retrieve All Borough's name"() {
        given:
        String mock = 'borough'
        when:
        Flowable flowable = client.retrieve(HttpRequest.GET('/all'), Argument.listOf(String))
        List<String> boroughName = flowable.firstElement().toSingle().blockingGet()
        then:
        boroughService.all() >> Single.just(['borough'])
        boroughName.find { it.toString() == mock }
    }

    @MockBean(BoroughService)
    IBoroughService boroughService() {
        Mock(IBoroughService)
    }
}
