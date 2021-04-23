import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TranslateModule } from '@ngx-translate/core';

import { StoreRoutingModule } from '@app/store/store-routing.module';
import { StoreComponent } from '@app/store/component/store.component';
import { NgxsModule } from '@ngxs/store';
import { StoreState } from '@app/store/store/store.state';


@NgModule({
  imports: [
    CommonModule,
    TranslateModule,
    StoreRoutingModule,
    NgxsModule.forFeature([StoreState])
  ],
  declarations: [StoreComponent]
})

export class StoreModule {
}
