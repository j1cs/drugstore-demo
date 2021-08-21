package me.jics

import io.micronaut.context.annotation.Requires
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.reactivex.rxjava3.core.Flowable

import java.time.LocalDate
import java.time.LocalTime

@Requires(property = 'spec.name', value = 'mockPharmacy')
@Controller('/pharmacies')
class MockPharmacyController {
    @Get
    Flowable<Pharmacy> index() {
        return Flowable.just(Pharmacy.builder()
                .date(LocalDate.now())
                .storeId('1')
                .storeName('store')
                .boroughName('borough')
                .locationName('location')
                .storeAddress('address')
                .openingHourOperation(LocalTime.now())
                .closingHourOperation(LocalTime.now())
                .storePhone('+56999999999')
                .storeLat(0.0)
                .storeLng(0.0)
                .openingHourOperation(LocalTime.now())
                .regionFk(1)
                .boroughFk(1)
                .build())
    }

}
