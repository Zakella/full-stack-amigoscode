import {Component, Inject, Input} from '@angular/core';
import {CustomerDTO} from "../../modules/customer-dto";

@Component({
  selector: 'app-customer-card',
  templateUrl: './customer-card.component.html',
  styleUrls: ['./customer-card.component.scss']
})
export class CustomerCardComponent {

  @Input()
  customer: CustomerDTO = {};

  @Input()
  customerIndex:number = 0;

}
