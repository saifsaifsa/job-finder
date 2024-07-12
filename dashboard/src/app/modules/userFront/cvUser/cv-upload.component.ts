import { Component } from '@angular/core';
import { CvService } from '../cvUser/cv.service';

@Component({
  selector: 'app-cv-upload',
  templateUrl: './cv-upload.component.html',
  styleUrls: ['./cv-upload.component.css']
})
export class CvUploadComponent {
  selectedFile: File = null;

  constructor(private cvService: CvService) {}

  onFileSelected(event): void {
    this.selectedFile = event.target.files[0];
  }

  onUpload(): void {
    if (this.selectedFile) {
      this.cvService.uploadCvPdf(1, this.selectedFile).subscribe(
        (response) => {
          console.log('CV uploaded successfully:', response);
        },
        (error) => {
          console.error('Error uploading CV:', error);
        }
      );
    }
  }
}
