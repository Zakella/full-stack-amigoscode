import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpHeaders, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {Observable} from "rxjs";
import {AuthenticationResponse} from "../../modules/authentication-response";
import {JwtHelperService} from "@auth0/angular-jwt";

@Injectable({
  providedIn: 'root'
})
export class HttpInterceptorService implements HttpInterceptor {

  constructor() {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    console.log("Request intercepted");

    const storedUser = localStorage.getItem("user");

    if (storedUser && storedUser !== "null") {
      const authResponce: AuthenticationResponse = JSON.parse(storedUser);
      const token = authResponce.token;

      if (token) {
        const autReq = req.clone({
            headers: new HttpHeaders({Authorization: "Bearer " + token})
          }
        );

        return next.handle(autReq);
      }
    }
    return next.handle(req);
  }
}
