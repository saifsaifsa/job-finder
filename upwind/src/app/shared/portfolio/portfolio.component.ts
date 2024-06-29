import { Component } from '@angular/core';
import { Lightbox } from 'ngx-lightbox';

@Component({
  selector: 'app-portfolio',
  templateUrl: './portfolio.component.html',
  styleUrls: ['./portfolio.component.scss']
})
export class PortfolioComponent {
  filteredRecord: any;

  constructor(public _lightbox: Lightbox) {}
  data = [
    {
      title: 'Iphone mockup',
      text: 'Abstract',
      src: 'assets/images/portfolio/1.jpg',
      type: 'Branding'
    },
    {
      title: 'Mockup Collection',
      text: 'Abstract',
      src: 'assets/images/portfolio/2.jpg',
      type: 'Designing'
    },
    {
      title: 'Abstract images',
      text: 'Abstract',
      src: 'assets/images/portfolio/3.jpg',
      type: 'Photography'
    },
    {
      title: 'Yellow bg with Books',
      text: 'Books',
      src: 'assets/images/portfolio/4.jpg',
      type: 'Development'
    },
    {
      title: 'Company V-card',
      text: 'V-card',
      src: 'assets/images/portfolio/5.jpg',
      type: 'Branding'
    },
    {
      title: 'Iphone mockup',
      text: 'Abstract',
      src: 'assets/images/portfolio/6.jpg',
      type: 'Branding'
    },
    {
      title: 'Mockup Collection',
      text: 'Abstract',
      src: 'assets/images/portfolio/7.jpg',
      type: 'Designing'
    },
    {
      title: 'Abstract images',
      text: 'Abstract',
      src: 'assets/images/portfolio/8.jpg',
      type: 'Photography'
    },
  ];

  
  open(index: number): void {
    this._lightbox.open(this.filteredRecord, index);
  }

  ngOnInit() {
    this.filteredRecord = this.data;
  }
}
