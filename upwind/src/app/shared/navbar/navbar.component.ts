import { Component, OnInit } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {
  target: any;
  active: any = '#home';
  currentRoute: any;

  constructor(public router: Router) {
    if (router.url !== '/index-four' && router.url !== '/index-five' && router.url !== '/index-seven')
      this.currentRoute = true;
  }
  windowScroll() {
    const navbar = document.getElementById("navbar");
    if (navbar != null) {
      if (
        document.body.scrollTop >= 50 ||
        document.documentElement.scrollTop >= 50
      ) {
        navbar.classList.add("is-sticky");
      } else {
        navbar.classList.remove("is-sticky");
      }
    }
  }

  ScrollIntoView(elem: string) {
    this.active = elem;
    let ele = document.querySelector(elem) as any;
    ele.scrollIntoView({ behavior: 'smooth', block: 'start' });
  }

  ngOnInit() {
    window.addEventListener('scroll', this.windowScroll, true);
  }

  toggleMenu() {
    let ele = document.getElementById('menu-collapse') as any;
    ele.classList.toggle('hidden');
  }
}
