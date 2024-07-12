
import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-edit-cv-dialog',
  templateUrl: './edit-cv-dialog.component.html'
})
export class EditCvDialogComponent implements OnInit {
  cvForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private dialogRef: MatDialogRef<EditCvDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {}

  ngOnInit(): void {
    this.cvForm = this.fb.group({
      name: [this.data.name],
      email: [this.data.email],
      // Initialize other form controls here
    });
  }

  onCancel(): void {
    this.dialogRef.close();
  }

  onSave(): void {
    // Implement save logic
    this.dialogRef.close(this.cvForm.value);
  }
}
