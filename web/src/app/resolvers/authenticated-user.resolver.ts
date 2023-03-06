import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot} from '@angular/router';
import {Observable, of} from 'rxjs';
import {User} from "../models/user";
import {SsoComponent} from "../components/authn/sso.component";
import {AuthService} from "../services/auth.service";
import {HttpErrorResponse, HttpStatusCode} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
/**
 * Resolve currently authenticated user.
 */
export class AuthenticatedUserResolver implements Resolve<User> {

  constructor(private authService: AuthService,
              private router: Router) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<User> {
    this.authService.getCurrentlyAuthenticatedUser().subscribe({
      next: (response) => {
        if (!response.body) {
          throw new Error('Undefined response body!');
        }

        console.log(response);
        return of(response.body);
      },
      error: (error) => {
        let httpError = <HttpErrorResponse>error;
        if (httpError.status == HttpStatusCode.Unauthorized) {
          console.log("Unauthorized! Routing to SsoComponent...");

          this.router.navigate(['/' + SsoComponent.navigationPath]);
        }
      }
    });

    throw new Error('Unable to resolve currently authenticated user!');
  }
}
