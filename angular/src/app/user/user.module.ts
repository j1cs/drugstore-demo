import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserComponent } from '@app/user/component/user.component';
import { TranslateModule } from '@ngx-translate/core';
import { UserRoutingModule } from '@app/user/user-routing.module';
import { ReactiveFormsModule } from '@angular/forms';
import { NgxsModule } from '@ngxs/store';
import { UserState } from '@app/user/store/user.state';
import { NgxsFormPluginModule } from '@ngxs/form-plugin';
import { SharedModule } from '@shared';

@NgModule({
  imports: [
    CommonModule,
    TranslateModule,
    UserRoutingModule,
    ReactiveFormsModule,
    NgxsModule.forFeature([UserState]),
    NgxsFormPluginModule,
    SharedModule,
  ],
  declarations: [UserComponent],
})
export class UserModule {}
