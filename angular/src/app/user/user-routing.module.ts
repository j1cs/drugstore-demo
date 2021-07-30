import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UserComponent } from '@app/user/component/user.component';
import { marker } from '@biesbjerg/ngx-translate-extract-marker';

const routes: Routes = [{ path: '', component: UserComponent, data: { title: marker('User') } }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class UserRoutingModule {}
