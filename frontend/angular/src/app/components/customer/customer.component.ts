import {Component, OnInit} from '@angular/core';
import {CustomerDTO} from "../../modules/customer-dto";
import {CustomerService} from "../../services/customer/customer.service";

@Component({
  selector: 'app-customer',
  templateUrl: './customer.component.html',
  styleUrls: ['./customer.component.scss']
})
export class CustomerComponent implements OnInit{
  display: boolean = false;
  right: string = "right";
  modal: boolean = false;

  customers: Array<CustomerDTO> = [];


  constructor( private customerService: CustomerService) {

  }

  ngOnInit(): void {
    this.findAllCustomers();


  }

  private findAllCustomers(){
    this.customerService.findAll()
      .subscribe({
        next: (data) =>{
          this.customers = data;
          console.log(data)
        }
      });
  }
}
