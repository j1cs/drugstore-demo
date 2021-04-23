import { Component, OnInit } from '@angular/core';
import { environment } from '@env/environment';
import { FormGroup, FormControl, Validators, FormBuilder } from '@angular/forms';
import { Observable } from 'rxjs';
import { Store, Select } from '@ngxs/store';
import { Store as StoreModel } from '@app/store/service/store';
import { StoreState } from '@app/store/store/store.state';
import { GetStores } from '@app/store/store/store.actions';

@Component({
  selector: 'app-store',
  templateUrl: './store.component.html',
  styleUrls: ['./store.component.scss']
})
export class StoreComponent implements OnInit {
  @Select(StoreState.getStores) stores$: Observable<StoreModel>;

  searchForm = new FormGroup({
    commune: new FormControl('recoleta'),
    name: new FormControl('ahumada'),
  });
  constructor(private store: Store) {
  }

  ngOnInit(): void {
    this.store.dispatch(new GetStores());
  }
}
