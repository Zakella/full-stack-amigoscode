import {Component} from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {


  constructor(private router: Router) {
  }

  navigateToPage3() {
    this.router.navigate(["page-3"])
      .then(r => alert("!!!"))

  }
}
