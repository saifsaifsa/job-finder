import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
    selector: 'app-answer-dialog',
    templateUrl: './answer-dialog.component.html',
})
export class AnswerDialogComponent {
    answerForm: FormGroup;

    constructor(
        public dialogRef: MatDialogRef<AnswerDialogComponent>,
        @Inject(MAT_DIALOG_DATA) public data: any,
        private fb: FormBuilder
    ) {
        this.answerForm = this.fb.group({
            content: ['', Validators.required],
            isCorrect: [false],
            score: [0, Validators.required]
        });
    }

    onSave(): void {
        if (this.answerForm.valid) {
            this.dialogRef.close(this.answerForm.value);
        }
    }

    onCancel(): void {
        this.dialogRef.close();
    }
}
