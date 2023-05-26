import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {AuthenticationRequest} from "../../modules/authentication-request";
import {Observable} from "rxjs";


@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor(
    private http: HttpClient
  ) {}

  login(authRequest:AuthenticationRequest):Observable<AuthenticatorResponse>{

    return this.http.post <AuthenticatorResponse>(
      'http://localhost:9988/api/v1/auth/login',
      authRequest
    )

  }
}
