import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpResponse} from "@angular/common/http";
import {CustomerDTO} from "../../modules/customer-dto";
import {map, Observable, throwError} from "rxjs";
import {AuthenticationResponse} from "../../modules/authentication-response";
import {environment} from "../../../environments/environment";
import {CustomerRegistrationRequest} from "../../modules/customer-registration-request";
import {CustomerUpdateRequest} from "../../modules/customer-update-request";

@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  private readonly customerURL = `${environment.api.baseUrl}${environment.api.customerURL}`;

  constructor(private http: HttpClient) {
  }


  findAll(): Observable<CustomerDTO[]> {

    return this.http.get<CustomerDTO[]>(this.customerURL);

  }

  registerCustomer(customer: CustomerRegistrationRequest): Observable<void> {
    return this.http.post <void>(this.customerURL, customer)
  }

  deleteCustomer(id: number | undefined): Observable<void> {
    return this.http.delete <void>(`${this.customerURL}/${id}`);

  }

   getCustomerById(id:  number): Observable<CustomerDTO> {
    return this.http.get(`${this.customerURL}/${id}`);
  }

  updateCustomer(id:  number| undefined, customer : CustomerUpdateRequest): Observable<void> {
    return this.http.put <void>(`${this.customerURL}/${id}`, customer);
  }


}
