import { Action, Selector, State, StateContext } from '@ngxs/store';
import { Store } from '@app/store/service/store';
import { AddStore, GetStores, GetStoresByBoroughAndName, RemoveStore } from '@app/store/store/store.actions';
import { Injectable } from '@angular/core';
import { StoreService } from '@app/store/service/store.service';
import { tap } from 'rxjs/operators';
import { Search } from '@app/store/service/search';

export class StoreForModel {
  model: Search;
  dirty: boolean;
  status: string;
  errors: object;
}

export class StoreStateModel {
  stores: Store[];
  form: StoreForModel;
}

@State<StoreStateModel>({
  name: 'stores',
  defaults: {
    stores: [],
    form: {
      model: undefined,
      dirty: false,
      status: '',
      errors: {}
    }
  }
})
@Injectable()
export class StoreState {

  @Selector()
  static getStores(state: StoreStateModel) {
    return state.stores;
  }

  @Selector()
  static getForm(state: StoreStateModel) {
    return state.form;
  }

  constructor(private storeService: StoreService) {
  }

  @Action(GetStores)
  get({ getState, setState }: StateContext<StoreStateModel>) {
    return this.storeService.getStores().pipe(
      tap(result => {
        const state = getState();
        setState({
          ...state,
          stores: result
        });
      })
    );
  }

  @Action(GetStoresByBoroughAndName)
  getByBoroughAndName({ getState, setState }: StateContext<StoreStateModel>) {
    return this.storeService.getStoresByBoroughAndName(getState().form.model).pipe(
      tap(result => {
        const state = getState();
        setState({
          ...state,
          stores: result
        });
      })
    );
  }


  @Action(AddStore)
  add({ getState, patchState }: StateContext<StoreStateModel>, { payload }: AddStore) {
    const state = getState();
    patchState({
      stores: [...state.stores, payload]
    });
  }

  @Action(RemoveStore)
  remove({ getState, patchState }: StateContext<StoreStateModel>, { payload }: RemoveStore) {
    patchState({
      stores: getState().stores.filter(s => s.name !== payload)
    });
  }
}
