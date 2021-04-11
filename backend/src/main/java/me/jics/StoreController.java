package me.jics;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.reactivex.Flowable;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotNull;

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
     * Retrieve all drugstores by region (now is "7" (RM)).
     *
     * @return @{link List} list with all stores
     */
    @Get(uri = "/all")
    public Flowable<Store> getAll() {
        return this.service.all();
    }

    /**
     * Retrieve all drugstores by region (now is "7" (RM)) and filtered by borough name
     *
     * @param borough name from RM.
     * @return @{link List} list with all stores filtered
     */
    @Get(uri = "/borough/{borough}")
    public Flowable<Store> getByBorough(@NotNull @PathVariable("borough") String borough) {
        return this.service.findByBorough(borough);
    }

    /**
     * Retrieve all drugstores by region (now is "7" (RM)) and filtered by store name
     *
     * @param name from the store
     * @return @{link List} lists with all stores filtered
     */
    @Get(uri = "/name/{name}")
    public Flowable<Store> getByName(@NotNull @PathVariable("name") String name) {
        return this.service.findByName(name);
    }

    /**
     * Retrieve all drugstores by region (now is "7" (RM)) and filtered by commune and store name
     *
     * @param borough name from RM.
     * @param name    from the store
     * @return @{link List} list with all stores filtered
     */
    @Get(uri = "/{borough}/{name}")
    public Flowable<Store> getByBoroughAndName(@NotNull @PathVariable("borough") String borough,
                                               @NotNull @PathVariable("name") String name) {
        return this.service.findByBoroughAndName(borough, name);
    }
}