import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup } from '@angular/forms';
import { UserService } from 'app/core/user/user.service';
import { User } from 'app/core/user/user.types';
import { QuizzesService } from 'app/modules/admin/quizz/quizz.service';

@Component({
    selector: 'app-quiz',
    templateUrl: './quiz.component.html',
})
export class QuizComponent implements OnInit {
    quiz: any;
    quizForm: FormGroup;
    quizId: any;
    user: User;

    constructor(
        private fb: FormBuilder,
        private quizService: QuizzesService,
        private route: ActivatedRoute,
        private userService: UserService
    ) {
        this.quizForm = this.fb.group({});
    }

    ngOnInit(): void {
        this.userService.getLoggedInUser().subscribe((data) => {
            this.user = data;
        });

        this.route.params.subscribe((params) => {
            this.quizId = params['id'];
            this.quizService.getQuizById(this.quizId).subscribe((data: any) => {
                this.quiz = data;
                this.quiz.questions.forEach((question) => {
                    this.quizForm.addControl(question.id.toString(), this.fb.control(null));
                });
            });
        });
    }

    submitAnswers(): void {
        const answers = [];
        for (const questionId in this.quizForm.value) {
            if (this.quizForm.value.hasOwnProperty(questionId)) {
                answers.push( this.quizForm.value[questionId]);
            }
        }
        this.quizService
            .submitAnswers(this.quizId, { userId: this.user.id, answers })
            .subscribe((result: any) => {
                if(result >= this.quiz.successScore){
                    
                }
            });
    }
}
