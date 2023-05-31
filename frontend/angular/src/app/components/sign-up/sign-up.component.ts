import {Component, Input} from '@angular/core';
import {CustomerRegistrationRequest} from "../../modules/customer-registration-request";
import {CustomerService} from "../../services/customer/customer.service";
import {error} from "@angular/compiler-cli/src/transformers/util";
import {Router} from "@angular/router";
import {MessageService} from "primeng/api";

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.scss']
})
export class SignUpComponent {
  constructor(private customerService:CustomerService,
              private router:Router ,
              private messageService: MessageService) {
  }
  customer: CustomerRegistrationRequest = {};

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
    const response =  this.customerService.registerCustomerAndAuthenticate(this.customer);
    console.log(response);
    response.subscribe(
      {
        next : (res) =>{
          console.log(res);
        }

      }
    )
    // this.customerService.registerCustomerAndAuthenticate(this.customer).subscribe(
    //   {
    //     next: (responce)=>{
    //       console.log(responce)
    //
    //       localStorage.setItem('user', JSON.stringify(responce))
    //       this.router.navigate(["customers"])
    //
    //
    //     },
    //     error : (err:any) =>{
    //       console.log(err);
    //        this.showError(err.error.message)
    //     }
    //
    //   }
    // );
  }

  showError(summary:string) {
    this.messageService.add(
      {
      severity: 'error',
      summary: summary,
      icon: "pi pi-times-circle"}
    );
  }


}
