package me.jics

import groovy.util.logging.Slf4j
import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import io.reactivex.Flowable
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

import javax.inject.Inject

@Slf4j
@MicronautTest
class BoroughControllerSpec extends Specification {

    @Shared
    @AutoCleanup
    @Inject
    @Client("/borough")
    RxHttpClient client

    @Inject
    IBoroughService boroughService

    void "Retrieve All Borough's name"() {
        given:
        String[] mock = getBoroughNames()
        when:
        Flowable<String> flowable = client.retrieve(HttpRequest.GET('/all'))
        flowable.blockingFirst()
        then:
        log.info "test"
    }

    static def getBoroughNames() {
        return { "boroughName" };
    }

    @MockBean(BoroughService)
    IBoroughService storeService() {
        Mock(IBoroughService)
    }
}
