import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs";
import {AuthenticationResponse} from "../models/authentication-response";
import {User} from "../models/user";

const baseURL = 'http://localhost:8080/authn';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private httpClient: HttpClient) {
  }

  getCurrentlyAuthenticatedUser(): Observable<HttpResponse<User>> {
    const headerDict = {
      'Content-Type': 'application/json',
      'Accept': 'application/json',
      'Access-Control-Allow-Headers': 'Content-Type',
      // 'Authorization': 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VybmFtZSIsImlhdCI6MTY3NzkzOTQ4OCwiZXhwIjoxNjc4MDI1ODg4fQ.XTYhA6fJEq22NGXdO4DDprw1JWd54W9EtIv3_uZG2aw5AoT6yCFKEIaPFyis1GtvfpiVpUKdAqPCpVCSD2B-YQ'
    };

    const url = 'http://localhost:8080/test/user';
    return this.httpClient.get<User>(url, {observe: 'response', headers: new HttpHeaders(headerDict)});
  }
}
