import { Component, OnInit } from '@angular/core';
import { environment } from '@env/environment';
import { FormGroup, FormControl, Validators, FormBuilder } from '@angular/forms';
import { Observable } from 'rxjs';
import { Store, Select } from '@ngxs/store';
import { Store as StoreModel } from '@app/store/service/store';
import { StoreState } from '@app/store/store/store.state';
import { GetStores, GetStoresByBoroughAndName } from '@app/store/store/store.actions';

@Component({
  selector: 'app-store',
  templateUrl: './store.component.html',
  styleUrls: ['./store.component.scss']
})
export class StoreComponent implements OnInit {
  @Select(StoreState.getStores) stores$: Observable<StoreModel>;

  searchForm = this.form.group({
    borough: ['', Validators.required],
    name: ['', Validators.required],
  });
  latitude = -33.4586361;
  longitude = -70.6419717;
  mapType = 'roadmap';

  constructor(private store: Store, private form: FormBuilder) {
  }

  ngOnInit(): void {
  }
  onSubmit(): void {
    this.store.dispatch(new GetStoresByBoroughAndName());
  }
}
