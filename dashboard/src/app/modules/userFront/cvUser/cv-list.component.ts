import { Component, OnInit } from '@angular/core';
import { Cv } from '../cvUser/cv.model';
import { CvService } from '../cvUser/cv.service';

@Component({
  selector: 'app-cv-list',
  templateUrl: './cv-list.component.html',
  styleUrls: ['./cv-list.component.css']
})
export class CvListComponent implements OnInit {
  cvs = [];
  selectedCv: Cv;

  constructor(private cvService: CvService) {}

  ngOnInit(): void {
    this.loadCvs();
  }

  loadCvs(): void {
    this.cvService.getAllCvs().subscribe(
      (cvs) => {
        this.cvs = cvs;
      },
      (error) => {
        console.error('Error loading CVs:', error);
      }
    );
  }

  generateCv(): void {
    this.cvService.generateCv().subscribe(
      (cv) => {
        this.cvs.push(cv);
        this.loadCvs()
      },
      (error) => {
        console.error('Error generating CV:', error);
      }
    );
  }

  downloadCv(id: number): void {
    this.cvService.downloadCvPdf(id).subscribe(
      (response) => {
        const url = window.URL.createObjectURL(response);
        const a = document.createElement('a');
        a.href = url;
        a.download = `cv_${id}.pdf`;
        a.click();
      },
      (error) => {
        console.error('Error downloading CV:', error);
      }
    );
  }
}
