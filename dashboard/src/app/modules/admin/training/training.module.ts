import { NgModule } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogModule } from '@angular/material/dialog';
import { MatTableModule } from '@angular/material/table';
import { Route, RouterModule } from '@angular/router';
import { TrainingComponent } from 'app/modules/admin/training/training.component';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { FuseAlertModule } from '@fuse/components/alert';
import { TrainingDetailComponent } from './trainingDetail.component';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';
const TrainingRoutes: Route[] = [
    {
        
        path     : '',
        component: TrainingComponent
    },
    {
        path     : 'add',
        component: TrainingDetailComponent
    },
    {
        path     : 'update/:id',
        component: TrainingDetailComponent
    }
];

@NgModule({
    declarations: [
        TrainingComponent,
        TrainingDetailComponent
    ],
    imports     : [
        RouterModule.forChild(TrainingRoutes),
        MatTableModule,
        MatButtonModule,
        MatDialogModule,
        MatFormFieldModule,
        FormsModule,
        ReactiveFormsModule,
        MatButtonModule,
        MatInputModule,
        MatFormFieldModule,
        MatSelectModule,
        MatIconModule,
        FuseAlertModule,
        CommonModule,
        MatSlideToggleModule,
        MatPaginatorModule,
        MatSortModule
    ]
})
export class TrainingModule
{
}
