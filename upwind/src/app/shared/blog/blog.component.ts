import { Component } from '@angular/core';

@Component({
  selector: 'app-blog',
  templateUrl: './blog.component.html',
  styleUrls: ['./blog.component.scss']
})
export class BlogComponent {
  blogData = [
    {
      image: 'assets/images/blog/1.jpg',
      title: 'Building Your Corporate Identity from Upwind',
      text: 'The phrasal sequence of the is now so that many campaign and benefit'
    },
    {
      image: 'assets/images/blog/2.jpg',
      title: 'The Right Hand of Business IT World',
      text: 'The phrasal sequence of the is now so that many campaign and benefit'
    },
    {
      image: 'assets/images/blog/3.jpg',
      title: 'Building Your Corporate Identity from Upwind',
      text: 'The phrasal sequence of the is now so that many campaign and benefit'
    }
  ]
}
