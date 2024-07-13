import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { QuizComponent } from './quiz.component';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';

const exampleRoutes: Routes = [
    {
        path: ':id',
        component: QuizComponent
    }
];

@NgModule({
    declarations: [
        QuizComponent
    ],
    imports: [
        RouterModule.forChild(exampleRoutes),
        CommonModule,
        ReactiveFormsModule
    ]
})
export class QuizModule { }
