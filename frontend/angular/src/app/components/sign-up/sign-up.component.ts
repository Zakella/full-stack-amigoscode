import {Component, Input} from '@angular/core';
import {CustomerRegistrationRequest} from "../../modules/customer-registration-request";

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.scss']
})
export class SignUpComponent {
  disable: boolean = true;

  @Input()
  customer: CustomerRegistrationRequest = {};

  // validateInput () : boolean {
  //   if ()
  // }

}
