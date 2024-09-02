import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { BoardComponent } from './component/board/board.component';
import { PageNotFoundComponent } from './component/page-not-found/page-not-found.component';
import { SettingsComponent } from './component/settings/settings.component';
import { LoginComponent } from './component/login/login.component';
import { AuthGuard } from './service/auth/auth.guard';
import { InfoComponent } from './component/info/info.component';

const routes: Routes = [
  {
    path: '',
    redirectTo: '/board',
    pathMatch: 'full',
  },
  {
    path: 'login',
    component: LoginComponent,
  },
  {
    path: 'board',
    component: BoardComponent,
  },
  {
    path: 'settings',
    component: SettingsComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'info',
    component: InfoComponent,
  },
  {
    path: '**',
    component: PageNotFoundComponent,
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
