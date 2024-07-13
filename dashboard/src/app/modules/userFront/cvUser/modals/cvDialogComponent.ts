// cv-dialog.component.ts
import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { TrainingCategories } from 'app/core/training/training.enums';

@Component({
    selector: 'app-cv-dialog',
    templateUrl: './cv-dialog.component.html',
})
export class cvDialogComponent {
    cvForm: FormGroup;

    constructor(
        public dialogRef: MatDialogRef<cvDialogComponent>,
        @Inject(MAT_DIALOG_DATA) public data: any,
        private fb: FormBuilder
    ) {
        this.cvForm = this.fb.group({
            name: [data?.name || '', Validators.required]
           
        });
    }

    onSave(): void {
        if (this.cvForm.valid) {
            this.dialogRef.close(this.cvForm.value);
        }
    }

    onCancel(): void {
        this.dialogRef.close();
    }
}
