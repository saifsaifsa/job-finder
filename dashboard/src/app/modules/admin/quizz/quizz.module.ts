import { NgModule } from '@angular/core';
import { Route, RouterModule } from '@angular/router';
import {MatPaginatorModule} from "@angular/material/paginator";
import {MatSortModule} from "@angular/material/sort";
import {MatTableModule} from "@angular/material/table";
import {AddQuizComponent} from "./add/add-quizz.component";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatNativeDateModule} from "@angular/material/core";
import {MatDatepickerModule} from "@angular/material/datepicker";
import {MatCheckboxModule} from "@angular/material/checkbox";
import {MatButtonModule} from "@angular/material/button";
import {MatInputModule} from "@angular/material/input";
import {ReactiveFormsModule} from "@angular/forms";
import {FuseAlertModule} from "../../../../@fuse/components/alert";
import {QuestionDialogComponent} from "./add/question-dialog.component";
import {AnswerDialogComponent} from "./answers/answer-dialog.component";
import {CommonModule} from "@angular/common";
import { QuizzComponent } from './quizz.component';

const exampleRoutes: Route[] = [
    {
        path     : 'skills/:id',
        component: QuizzComponent
    },{
        path     : 'skills/:id/add',
        component: AddQuizComponent
    },{
        path: 'skills/:id/edit/:quizId',
        component: AddQuizComponent
    }
];

@NgModule({
    declarations: [
        QuizzComponent,
        AddQuizComponent,
        QuestionDialogComponent,
        AnswerDialogComponent
    ],
    imports: [
        RouterModule.forChild(exampleRoutes),
        MatPaginatorModule,
        MatSortModule,
        MatTableModule,
        MatFormFieldModule,
        ReactiveFormsModule,
        MatFormFieldModule,
        MatInputModule,
        MatButtonModule,
        MatCheckboxModule,
        MatDatepickerModule,
        MatNativeDateModule,
        FuseAlertModule,
        CommonModule
    ]
})
export class QuizzModule
{
}
