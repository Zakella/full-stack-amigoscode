import {inject, NgModule} from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {CustomerComponent} from "./components/customer/customer.component";
import {LoginComponent} from "./components/login/login.component";
import {AccessGuardService} from "./services/guard/access-guard.service";
import {AuthGuard} from "@auth0/auth0-angular";
import {SignUpComponent} from "./components/sign-up/sign-up.component";

const routes: Routes = [

  {
    path: "customers",
    component: CustomerComponent,
    canActivate: [AccessGuardService]
  },

  {
    path: "login",
    component: LoginComponent,

  },

  {
    path: "signup",
    component: SignUpComponent,

  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
