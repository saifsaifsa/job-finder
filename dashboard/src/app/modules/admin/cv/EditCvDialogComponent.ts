// edit-cv-dialog.component.ts
import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-edit-cv-dialog',
  templateUrl: './edit-cv-dialog.component.html'
})
export class EditCvDialogComponent {
  editCvForm: FormGroup;

  constructor(
    public dialogRef: MatDialogRef<EditCvDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private fb: FormBuilder
  ) {
    this.editCvForm = this.fb.group({
      name: [data.user.name, Validators.required],
      email: [data.user.email, [Validators.required, Validators.email]],
      username: [data.user.username, Validators.required],
      views: [data.views, [Validators.required, Validators.min(0)]],
      downloads: [data.downloads, [Validators.required, Validators.min(0)]]
    });
  }

  getErrorMessage(controlName: string): string {
    const control = this.editCvForm.controls[controlName];
    if (control.hasError('required')) {
      return 'You must enter a value';
    }
    if (control.hasError('email')) {
      return 'Not a valid email';
    }
    if (control.hasError('min')) {
      return 'Value must be greater than or equal to 0';
    }
    return '';
  }

  onSubmit(): void {
    if (this.editCvForm.valid) {
      this.dialogRef.close(this.editCvForm.value);
    }
  }

  onCancel(): void {
    this.dialogRef.close();
  }
}
