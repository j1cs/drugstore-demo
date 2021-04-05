import { Component, OnInit, Input } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { AppService } from './app.service';
import { Store } from './store';

@Component({
  selector: 'ngDrugstore-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {

  searchForm = new FormGroup({
    commune: new FormControl('recoleta'),
    name: new FormControl('ahumada'),
  });
  stores: Store[];
  latitude = -33.4586361;
  longitude = -70.6419717;
  mapType = 'roadmap';
  constructor(private service: AppService) {}

  ngOnInit(): void {
    this.getStores(this.searchForm.value.commune, this.searchForm.value.name);
  }

  onSubmit(): void {
    this.getStores(this.searchForm.value.commune, this.searchForm.value.name);
  }
  getStores(commune: string, name: string): void {
    this.service.getStoresByCommuneAndName(commune, name)
    .subscribe(stores => this.stores = stores);
  }
}
