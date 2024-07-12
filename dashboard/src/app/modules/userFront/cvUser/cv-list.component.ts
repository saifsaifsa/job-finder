import { Component, OnInit } from '@angular/core';
import { Cv } from '../cvUser/cv.model';
import { CvService } from '../cvUser/cv.service';

@Component({
  selector: 'app-cv-list',
  templateUrl: './cv-list.component.html',
  styleUrls: ['./cv-list.component.css']
})
export class CvListComponent implements OnInit {
  cvs: Cv[] = [];
  selectedCv: Cv;
  editMode: boolean = false;

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
        this.loadCvs();
      },
      (error) => {
        console.error('Error generating CV:', error);
      }
    );
  }

  deleteCv(id: number): void {
    this.cvService.deleteCv(id).subscribe(
      () => {
        this.cvs = this.cvs.filter(cv => cv.id !== id);
        console.log('CV deleted successfully');
      },
      (error) => {
        console.error('Error deleting CV:', error);
      }
    );
  }

  editCv(cv: Cv): void {
    this.selectedCv = { ...cv };
    this.editMode = true;
  }

  saveCv(): void {
    this.cvService.updateCv(this.selectedCv).subscribe(
      (updatedCv) => {
        this.cvs = this.cvs.map(cv => cv.id === updatedCv.id ? updatedCv : cv);
        this.editMode = false;
        console.log('CV updated successfully:', updatedCv);
      },
      (error) => {
        console.error('Error updating CV:', error);
      }
    );
  }

  closeModal(): void {
    this.editMode = false;
  }
}
