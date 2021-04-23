import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { marker } from '@biesbjerg/ngx-translate-extract-marker';

import { StoreComponent } from '@app/store/component/store.component';

const routes: Routes = [
  { path: '', component: StoreComponent, data: { title: marker('Store') } }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: []
})
export class StoreRoutingModule {
}
