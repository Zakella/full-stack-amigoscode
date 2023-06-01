import {Component, Input} from '@angular/core';
import {CustomerRegistrationRequest} from "../../modules/customer-registration-request";
import {CustomerService} from "../../services/customer/customer.service";
import {error} from "@angular/compiler-cli/src/transformers/util";
import {Router} from "@angular/router";
import {MessageService} from "primeng/api";
import {AuthenticationService} from "../../services/authentication/authentication.service";
import {AuthenticationRequest} from "../../modules/authentication-request";

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.scss']
})
export class SignUpComponent {
  constructor(private authenticationService: AuthenticationService,
              private router: Router,
              private messageService: MessageService,
              private customerService: CustomerService,) {
  }

  customer: CustomerRegistrationRequest = {};

  authRequest: AuthenticationRequest = {}

  get isCustomerValid(): boolean {
    return this.hasLength(this.customer.name) &&
      this.hasLength(this.customer.email) &&
      this.customer.age != undefined && this.customer.age > 0 &&
      this.hasLength(this.customer.password) &&
      this.hasLength(this.customer.gender)
      ;
  }

  private hasLength(input: string | undefined): boolean {
    return input !== null && input != undefined && input.length > 0;
  }


  registerCustomer(): void {

     this.customerService.registerCustomer(this.customer)
      .subscribe(
        {
          next: () => {
            this.authRequest.username = this.customer.email;
            this.authRequest.password = this.customer.password;
            this.authenticationService.login(this.authRequest)
              .subscribe(
                {
                  next:(authResponce) =>{
                    console.log(authResponce)
                    localStorage.setItem('user', JSON.stringify(authResponce))
                    this.router.navigate(["customers"])
                  },
                  error:(err) => {
                    console.log(err.error.statusCode);

                  }
                }
              );


          },
          error: (err) => {
            this.showError(err.error.message)
          }
        }
      )
  }

  showError(summary: string) {
    this.messageService.add(
      {
        severity: 'error',
        summary: summary,
        icon: "pi pi-times-circle"
      }
    );
  }



}
