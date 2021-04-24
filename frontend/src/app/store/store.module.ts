import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TranslateModule } from '@ngx-translate/core';

import { StoreRoutingModule } from '@app/store/store-routing.module';
import { StoreComponent } from '@app/store/component/store.component';
import { NgxsModule } from '@ngxs/store';
import { StoreState } from '@app/store/store/store.state';
import { ReactiveFormsModule } from '@angular/forms';
import { NgxsFormPluginModule } from '@ngxs/form-plugin';
import { GoogleMapsModule } from '@angular/google-maps';


@NgModule({
  imports: [
    CommonModule,
    TranslateModule,
    StoreRoutingModule,
    ReactiveFormsModule,
    NgxsModule.forFeature([StoreState]),
    NgxsFormPluginModule,
    GoogleMapsModule
  ],
  declarations: [StoreComponent]
})

export class StoreModule {
}
