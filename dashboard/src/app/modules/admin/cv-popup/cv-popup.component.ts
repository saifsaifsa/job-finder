import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-cv-popup',
  templateUrl: './cv-popup.component.html',
  styleUrls: ['./cv-popup.component.scss']
})
export class CvPopupComponent {
  constructor(
    public dialogRef: MatDialogRef<CvPopupComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {}

  onNoClick(): void {
    this.dialogRef.close();
  }
}
