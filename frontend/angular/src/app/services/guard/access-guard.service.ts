import { Injectable } from '@angular/core';
import {ActivatedRouteSnapshot, Router, RouterStateSnapshot, UrlTree} from "@angular/router";
import {Observable} from "rxjs";
import {AuthenticationResponse} from "../../modules/authentication-response";
import {JwtHelperService} from "@auth0/angular-jwt";

interface CanActivate {
  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree
}

@Injectable({
  providedIn: 'root'
})
export class AccessGuardService implements CanActivate{

  constructor(private router:Router) {
  }
  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    const storedUser = localStorage.getItem("user");
    if(storedUser){
      const authResponce:AuthenticationResponse = JSON.parse(storedUser);
      const token = authResponce.token;
      if(token){
        const jwtHelper:JwtHelperService = new JwtHelperService();
        const isTokenNonExpired = !jwtHelper.isTokenExpired(token);
        if(isTokenNonExpired){
          return true;
        }
      }
    }

    this.router.navigate(['login']);
    return false;

  }

}
