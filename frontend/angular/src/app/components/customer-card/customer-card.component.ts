import {Component, EventEmitter, Inject, Input, Output} from '@angular/core';
import {CustomerDTO} from "../../modules/customer-dto";
import {ConfirmationService, ConfirmEventType, MessageService} from "primeng/api";
import {CustomerService} from "../../services/customer/customer.service";

@Component({
  selector: 'app-customer-card',
  templateUrl: './customer-card.component.html',
  styleUrls: ['./customer-card.component.scss'],
})
export class CustomerCardComponent {

  @Input()
  customer: CustomerDTO = {};

  @Input()
  customerIndex:number = 0;

  @Output()
  delete : EventEmitter<CustomerDTO> = new EventEmitter<CustomerDTO>();


  @Output()
  update : EventEmitter<CustomerDTO> = new EventEmitter<CustomerDTO>();

  display: boolean = false;

  constructor(private confirmationService: ConfirmationService,
              private messageService: MessageService ,
              private customerService : CustomerService) {}

  get customerImage () :string {
    const gender = this.customer.gender === 'MALE' ? 'men': 'women'
    return `https://randomuser.me/api/portraits/${gender}/${this.customerIndex}.jpg`;
  }


  onDelete() {
    this.delete.emit(this.customer);
  }

  onUpdate() {
    this.update.emit(this.customer);
  }


}


