import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  title = 'upwind';

  ngOnInit() {
    document.documentElement.setAttribute('dir', 'ltr');
  }
}
