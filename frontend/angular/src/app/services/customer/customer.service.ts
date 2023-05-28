import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {CustomerDTO} from "../../modules/customer-dto";
import {map, Observable, throwError} from "rxjs";
import {AuthenticationResponse} from "../../modules/authentication-response";
import {environment} from "../../../environments/environment";
import {CustomerRegistrationRequest} from "../../modules/customer-registration-request";

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


}
