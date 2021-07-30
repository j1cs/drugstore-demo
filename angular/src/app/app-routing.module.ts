import { NgModule } from '@angular/core';
import { PreloadAllModules, RouterModule, Routes } from '@angular/router';
import { Shell } from '@app/shell/shell.service';

const routes: Routes = [
  Shell.childRoutes([
    { path: 'store', loadChildren: () => import('./store/store.module').then((m) => m.StoreModule) },
    { path: 'user', loadChildren: () => import('./user/user.module').then((m) => m.UserModule) },
    { path: 'about', loadChildren: () => import('./about/about.module').then((m) => m.AboutModule) },
  ]),
  // Fallback when no prior route is matched
  { path: '**', redirectTo: '', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { preloadingStrategy: PreloadAllModules })],
  exports: [RouterModule],
  providers: [],
})
export class AppRoutingModule {}
