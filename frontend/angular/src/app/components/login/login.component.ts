import { Component } from '@angular/core';
import {AuthenticationRequest} from "../../modules/authentication-request";
import {AuthenticationService} from "../../services/authentication/authentication.service";
import {Observable} from "rxjs";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {

  authRequest: AuthenticationRequest = {}


  errorMsg = '';

  constructor(
    private  authentication:AuthenticationService,
    private router:Router


  ) {}
  login() {
    this.errorMsg  = '';
    this.authentication.login(this.authRequest)
      .subscribe(
        {
          next:(authResponce) =>{
           console.log(authResponce)
            localStorage.setItem('user', JSON.stringify(authResponce))
            this.router.navigate(["customers"])
          },
          error:(err) => {
            console.log(err.error.statusCode);
            if (err.error.statusCode === 401) {
              this.errorMsg = 'Login and / or password is incorrect'
            }
          }
        }
      )


  }

  signUp() {
    this.router.navigate(['signup'])

  }
}
