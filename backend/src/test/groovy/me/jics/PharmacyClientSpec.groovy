package me.jics

import io.micronaut.context.ApplicationContext
import io.micronaut.runtime.server.EmbeddedServer
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject

class PharmacyClientSpec extends Specification {

    @AutoCleanup
    @Shared
    EmbeddedServer server = ApplicationContext.run(EmbeddedServer) as EmbeddedServer

    @Shared
    @Subject
    PharmacyClient client = server.applicationContext.getBean(PharmacyClient);

    void "Fetch all Pharmacies"() {
        when:
        Pharmacy pharmacy = client.retrieve().blockingFirst()
        then:
        pharmacy.getStoreId() == '1'
    }
}
