import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs";
import {User} from "../models/user";

const baseURL = 'http://localhost:8080/backend-1.0-SNAPSHOT/oneapp/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private httpClient: HttpClient) {
  }

  get(uuid: string): Observable<HttpResponse<User>> {
    const postBody = {
      uuid: uuid
    }

    return this.httpClient.post<User>(baseURL + '/find', postBody, {observe: 'response'});
  }

  update(fullName: string,
         pronouns: string,
         pronunciation: string,
         email: string,
         location: string,
         bio: string,
         jobTitle: string,
         organization: string,
         skype: string,
         linkedin: string,
         twitter: string,
         facebook: string,
         websiteUrl: string): Observable<HttpResponse<User>> {

    const postBody = {
      fullName: fullName,
      pronouns: pronouns,
      pronunciation: pronunciation,
      email: email,
      location: location,
      bio: bio,
      jobTitle: jobTitle,
      organization: organization,
      skype: skype,
      linkedin: linkedin,
      twitter: twitter,
      facebook: facebook,
      websiteUrl: websiteUrl,
    }

    return this.httpClient.post<User>(baseURL + '/update', postBody, {observe: 'response'});
  }

  updatePassword(
    uuid: string,
    currentPassword: string,
    newPassword: string,
    confirmPassword: string) {

    const postBody = {
      uuid: uuid,
      currentPassword: currentPassword,
      newPassword: newPassword,
      confirmPassword: confirmPassword
    }

    return this.httpClient.post(baseURL + '/update-password', postBody, {observe: 'response'});
  }
}
