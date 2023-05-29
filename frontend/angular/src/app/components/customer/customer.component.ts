import {Component, OnInit} from '@angular/core';
import {CustomerDTO} from "../../modules/customer-dto";
import {CustomerService} from "../../services/customer/customer.service";
import {CustomerRegistrationRequest} from "../../modules/customer-registration-request";
import {ConfirmationService, MessageService} from "primeng/api";

@Component({
  selector: 'app-customer',
  templateUrl: './customer.component.html',
  styleUrls: ['./customer.component.scss']
})
export class CustomerComponent implements OnInit {
  display: boolean = false;

  customers: Array<CustomerDTO> = [];

  customer: CustomerRegistrationRequest = {};

  operation : 'create'| 'update' = 'create';


  constructor(private customerService: CustomerService,
              private messageService: MessageService,
              private confirmationService: ConfirmationService
  ) {

  }

  ngOnInit()
    :
    void {
    this.findAllCustomers();


  }


  findAllCustomers() {
    this.customerService.findAll()
      .subscribe({
        next: (data) => {
          this.customers = data;
          console.log(data)
        }
      });
  }

  showSuccessMessage(summary: string, detail: string) {
    this.messageService.add({
      severity: 'success',
      summary: summary,
      detail: detail
    });
  }

  save(customer
         :
         CustomerRegistrationRequest
  ) {

    console.log(customer);
    if (customer) {
      this.customerService.registerCustomer(customer)
        .subscribe(
          {
            next: () => {
              this.findAllCustomers();
              this.display = false;
              this.customer = {};
              this.showSuccessMessage("Customer added successfully", `Customer ${this.customer.name} was saved`);
            }
          }
        );
    }
  }


  deleteCustomer(customer
                   :
                   CustomerDTO
  ) {
    console.log();

    this.confirmationService.confirm({
      header: "Delete customer",
      message: `Are you sure you want to delete ${customer.name}?`,
      accept: () => {
        this.customerService.deleteCustomer(customer.id)
          .subscribe({
            next: () => {
              this.findAllCustomers();
              this.showSuccessMessage("Customer deleted successfully", `Customer ${this.customer.name} was deleted`)
            }
          });
      }
    });

  }
}
