import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import {SidebarComponent} from './components/sidebar/sidebar.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {UserProfileComponent} from './components/user-profile/user-profile.component';
import {HeaderComponent} from './components/header/header.component';
import {DashboardComponent} from './components/dashboard/dashboard.component';
import {PageNotFoundComponent} from './components/page-not-found/page-not-found.component';
import { SsoComponent } from './components/authn/sso.component';

@NgModule({
  declarations: [
    AppComponent,
    SidebarComponent,
    UserProfileComponent,
    HeaderComponent,
    DashboardComponent,
    PageNotFoundComponent,
    SsoComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule,
    NgbModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
