package me.jics;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.reactivex.Single;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Restful API to get all drugstores from RM.
 *
 * @author Juan Cuzmar
 * @version 1.0
 */
@Slf4j
@Controller("/store")
public class StoreController {

    private final IStoreService service;

    /**
     * Injected dependencies.
     *
     * @param service StoreService layer
     */
    public StoreController(IStoreService service) {
        this.service = service;
    }


    /**
     * Retrieve all drugstores by region (now is "7" (RM))
     *
     * @return Flowable with all stores
     */
    @Get(uri = "/all")
    public Single<List<Store>> getAll() {
        log.info("Entering to StoreController.getAll");
        return this.service.all();
    }

    /**
     * Retrieve all drugstores name by region (now is "7" (RM))
     *
     * @return Flowable with all stores
     */
    @Get(uri = "/all/names")
    public Single<List<String>> getAllStoresName() {
        log.info("Entering to StoreController.getAllStoresName");
        return this.service.allNames();
    }

    /**
     * Retrieve all drugstores by region (now is "7" (RM)) and filtered by borough name
     *
     * @param borough name from RM to filter
     * @return Flowable with all stores filtered
     */
    @Get(uri = "/borough/{borough}")
    public Single<List<Store>> getByBorough(@NotNull @PathVariable("borough") String borough) {
        log.info("Entering to StoreController.getByBorough with borough:{}", borough);
        return this.service.findByBorough(borough);
    }

    /**
     * Retrieve all drugstores by region (now is "7" (RM)) and filtered by store name
     *
     * @param name from the store to filter
     * @return Flowable with all stores filtered
     */
    @Get(uri = "/name/{name}")
    public Single<List<Store>> getByName(@NotNull @PathVariable("name") String name) {
        log.info("Entering to StoreController.getByName with name:{}", name);
        return this.service.findByName(name);
    }

    /**
     * Retrieve all drugstores by region (now is "7" (RM)) and filtered by commune and store name
     *
     * @param borough name from RM to filter
     * @param name    from the store to filter
     * @return Flowable with all stores filtered
     */
    @Get(uri = "/{borough}/{name}")
    public Single<List<Store>> getByBoroughAndName(@NotNull @PathVariable("borough") String borough,
                                               @NotNull @PathVariable("name") String name) {
        log.info("Entering to StoreController.getByBoroughAndName with borough:{} and name:{}", borough, name);
        return this.service.findByBoroughAndName(borough, name);
    }
}