import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {UserProfileComponent} from "./components/user-profile/user-profile.component";
import {DashboardComponent} from "./components/dashboard/dashboard.component";
import {PageNotFoundComponent} from "./components/page-not-found/page-not-found.component";
import {SsoComponent} from "./components/authn/sso.component";
import {AuthenticatedUserResolver} from "./resolvers/authenticated-user.resolver";

// TODO: Make less round trip of AuthenticatedUserResolver (maybe use localstorage or find another open standard for this scenario)
const routes: Routes = [
  {path: '', redirectTo: UserProfileComponent.navigationPath, pathMatch: 'full'},
  {
    path: UserProfileComponent.navigationPath,
    component: UserProfileComponent,
    resolve: {
      authenticatedUser: AuthenticatedUserResolver
    },
  },
  {
    path: DashboardComponent.navigationPath,
    component: DashboardComponent,
    resolve: {
      authenticatedUser: AuthenticatedUserResolver
    },
  },
  {
    path: SsoComponent.navigationPath,
    component: SsoComponent,
  },
  {path: '**', component: PageNotFoundComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
