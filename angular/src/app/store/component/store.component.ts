import { Component, OnInit } from '@angular/core';
import { Validators, FormBuilder } from '@angular/forms';
import { actionsExecuting, ActionsExecuting } from '@ngxs-labs/actions-executing';
import { Observable } from 'rxjs';
import { Store, Select } from '@ngxs/store';
import { Store as StoreModel } from '@app/store/service/store';
import { StoreState } from '@app/store/store/store.state';
import { GetBoroughs, GetStoreNames, GetStoresByBoroughAndName } from '@app/store/store/store.actions';

@Component({
  selector: 'app-store',
  templateUrl: './store.component.html',
  styleUrls: ['./store.component.scss'],
})
export class StoreComponent implements OnInit {
  @Select(actionsExecuting([GetStoreNames, GetBoroughs])) isFirstLoading$: Observable<ActionsExecuting>;
  @Select(actionsExecuting([GetStoresByBoroughAndName])) isLoading$: Observable<ActionsExecuting>;

  @Select(StoreState.getStores) stores$: Observable<StoreModel>;
  @Select(StoreState.getStoreNames) storeNames$: Observable<StoreModel>;
  @Select(StoreState.getBoroughs) boroughs$: Observable<StoreModel>;

  searchForm = this.form.group({
    borough: ['', Validators.required],
    name: ['', Validators.required],
  });

  zoom = 15;

  constructor(private store: Store, private form: FormBuilder) {}

  ngOnInit(): void {
    this.store.dispatch([new GetStoreNames(), new GetBoroughs()]);
  }

  onSubmit(): void {
    this.store.dispatch(new GetStoresByBoroughAndName());
  }
}
