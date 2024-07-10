// skill-dialog.component.ts
import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
    selector: 'app-skill-dialog',
    templateUrl: './skill-dialog.component.html',
})
export class SkillDialogComponent {
    skillForm: FormGroup;

    constructor(
        public dialogRef: MatDialogRef<SkillDialogComponent>,
        @Inject(MAT_DIALOG_DATA) public data: any,
        private fb: FormBuilder
    ) {
        this.skillForm = this.fb.group({
            name: [data?.name || '', Validators.required],
            category: [data?.category || '', Validators.required],
        });
    }

    onSave(): void {
        if (this.skillForm.valid) {
            this.dialogRef.close(this.skillForm.value);
        }
    }

    onCancel(): void {
        this.dialogRef.close();
    }
}
