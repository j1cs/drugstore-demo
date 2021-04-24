import { State, Action, StateContext, Selector } from '@ngxs/store';
import { Store } from '@app/store/service/store';
import { GetStores, AddStore, RemoveStore, GetStoresByBoroughAndName } from '@app/store/store/store.actions';
import { Injectable } from '@angular/core';
import { StoreService } from '@app/store/service/store.service';
import { tap } from 'rxjs/operators';
import { Form } from '@app/store/service/form';

export class StoreStateModel {
  stores: Store[];
  form: Form;
}

@State<StoreStateModel>({
  name: 'stores',
  defaults: {
    stores: [],
    form: {
      borough: '',
      name: ''
    }
  }
})
@Injectable()
export class StoreState {

  @Selector()
  static getStores(state: StoreStateModel) {
    return state.stores;
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
  getByBoroughAndName({ getState, setState }: StateContext<StoreStateModel>, {
    payload
  }: GetStoresByBoroughAndName) {
    return this.storeService.getStoresByBoroughAndName(payload).pipe(
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
