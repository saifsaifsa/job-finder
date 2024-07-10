import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
    selector: 'app-question-dialog',
    templateUrl: './answer-dialog.component.html',
})
export class AnswerDialogComponent {
    questionForm: FormGroup;

    constructor(
        public dialogRef: MatDialogRef<AnswerDialogComponent>,
        @Inject(MAT_DIALOG_DATA) public data: any,
        private fb: FormBuilder
    ) {
        this.questionForm = this.fb.group({
            content: [data.content || '', Validators.required],
            isCorrect: [data.isCorrect || false],
            score: [data.score || 0, [Validators.required, Validators.min(0)]]
        });
    }

    onSave(): void {
        if (this.questionForm.valid) {
            this.dialogRef.close(this.questionForm.value);
        }
    }

    onCancel(): void {
        this.dialogRef.close();
    }
}
