import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Cv } from '../cvUser/cv.model';
import { CvService } from '../cvUser/cv.service';

@Component({
  selector: 'app-cv-detail',
  templateUrl: './cv-detail.component.html'
})
export class CvDetailComponent implements OnInit {
  cv: Cv;

  constructor(
    private route: ActivatedRoute,
    private cvService: CvService
  ) {}

  ngOnInit(): void {
    this.loadCv();
  }

  loadCv(): void {
    const id = +this.route.snapshot.paramMap.get('id');
    this.cvService.getCv(id).subscribe(
      (cv) => {
        this.cv = cv;
      },
      (error) => {
        console.error('Error loading CV:', error);
      }
    );
  }

  saveCv(): void {
    this.cvService.updateCv(this.cv).subscribe(
      (updatedCv) => {
        this.cv = updatedCv;
        console.log('CV updated successfully:', updatedCv);
      },
      (error) => {
        console.error('Error updating CV:', error);
      }
    );
  }
}
