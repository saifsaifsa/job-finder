import { Component } from '@angular/core';
import { CvService } from '../cvUser/cv.service';
import { Cv } from '../cvUser/cv.model';

@Component({
  selector: 'app-cv-generate',
  templateUrl: './cv-generate.component.html'
})
export class CvGenerateComponent {
  newCv: Cv;

  constructor(private cvService: CvService) {}

  generateCv(): void {
    this.cvService.generateCv().subscribe(
      (cv) => {
        this.newCv = cv;
        console.log('CV generated successfully:', cv);
      },
      (error) => {
        console.error('Error generating CV:', error);
      }
    );
  }
}
