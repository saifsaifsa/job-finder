import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormArray } from '@angular/forms';
import { QuizzesService } from "../quizz.service";
import { MatDialog } from "@angular/material/dialog";
import { QuestionDialogComponent } from "./question-dialog.component";
import { AnswerDialogComponent } from "../answers/answer-dialog.component";
import { ActivatedRoute, Router } from '@angular/router';

@Component({
    selector: 'app-add-quiz',
    templateUrl: './add-quizz.component.html',
})
export class AddQuizComponent implements OnInit {
    quizForm: FormGroup;
    alert: { type: string, message: string } = { type: '', message: '' };
    showAlert = false;
    competanceId: number;
    quizId: number | null = null; // New property to hold quiz ID for updates

    constructor(
        private fb: FormBuilder,
        private quizService: QuizzesService,
        private dialog: MatDialog,
        private readonly route: ActivatedRoute,
        private router: Router
    ) {
        this.quizForm = this.fb.group({
            title: ['', Validators.required],
            successScore: [0, [Validators.required, Validators.min(1)]],
            questions: this.fb.array([]) // Keep questions as form array to manage answers
        });
    }

    ngOnInit(): void {
        this.route.params.subscribe((params) => {
            this.competanceId = params["id"];
            this.quizId = params["quizId"] || null;

            if (this.quizId) {
                this.loadQuizDetails(this.quizId);
            }
        });
    }

    // Method to load quiz details for update
    loadQuizDetails(quizId): void {
        this.quizService.getQuizById(quizId).subscribe((quiz) => {
            this.quizForm.patchValue({
                title: quiz.title,
                successScore: quiz.successScore,
                
            });
            quiz.questions.forEach((question) => {
                const questionGroup = this.fb.group({
                    content: question.content,
                    id:question.id,
                    answers: this.fb.array(
                        question.answers.map((answer) => this.fb.group(answer))
                    )
                });
                this.questions.push(questionGroup);
            });
        });
    }

    get questions(): FormArray {
        return this.quizForm.get('questions') as FormArray;
    }

    addQuestion(): void {
        const dialogRef = this.dialog.open(QuestionDialogComponent, {
            width: '400px',
            data: { title: 'Add Question' }
        });

        dialogRef.afterClosed().subscribe(result => {
            if (result) {
                this.questions.push(this.fb.group({
                    content: result.content,
                    answers: this.fb.array([]) // Initialize empty answers array
                }));
            }
        });
    }

    removeQuestion(index: number) {
        this.questions.removeAt(index);
    }

    getAnswers(questionIndex: number): FormArray {        
        return this.questions.at(questionIndex).get('answers') as FormArray;
    }

    addAnswer(questionIndex: number) {
        const dialogRef = this.dialog.open(AnswerDialogComponent, {
            width: '400px',
            data: { title: 'Add Answer' }
        });

        dialogRef.afterClosed().subscribe(result => {
            if (result) {
                this.getAnswers(questionIndex).push(this.fb.group(result));
            }
        });
    }

    removeAnswer(questionIndex: number, answerIndex: number) {
        this.getAnswers(questionIndex).removeAt(answerIndex);
    }

    onSubmit() {
        if (this.quizForm.invalid) {
            this.showAlert = true;
            this.alert.type = 'error';
            this.alert.message = 'Please fill out the form correctly.';
            return;
        }

        if (this.quizId) {
            // Update quiz logic
            this.quizService.updateQuiz(this.quizId, this.quizForm.value).subscribe({
                next: () => {
                    this.showAlert = true;
                    this.alert.type = 'success';
                    this.alert.message = 'Quiz updated successfully!';
                    this.router.navigate(['/quizzes']);
                },
                error: () => {
                    this.showAlert = true;
                    this.alert.type = 'error';
                    this.alert.message = 'Failed to update quiz. Please try again.';
                }
            });
        } else {
            // Add new quiz logic
            this.quizService.addQuizzes(this.quizForm.value, this.competanceId).subscribe({
                next: () => {
                    this.showAlert = true;
                    this.alert.type = 'success';
                    this.alert.message = 'Quiz added successfully!';
                    this.quizForm.reset();
                    this.questions.clear();
                },
                error: () => {
                    this.showAlert = true;
                    this.alert.type = 'error';
                    this.alert.message = 'Failed to add quiz. Please try again.';
                }
            });
        }
    }
}
