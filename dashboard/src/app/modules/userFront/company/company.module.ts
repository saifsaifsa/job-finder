import { NgModule } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogModule } from '@angular/material/dialog';
import { MatTableModule } from '@angular/material/table';
import { Route, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { FuseAlertModule } from '@fuse/components/alert';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';
import { CompanyComponentUser } from './company.component';
import { CompanyDetailComponentUser } from './companyDetail.component';
import { MatGridList, MatGridListModule } from '@angular/material/grid-list';
const CompanyRoutes: Route[] = [
    {   
        path     : '',
        component: CompanyComponentUser
    },
    {
        path     : ':id',
        component: CompanyDetailComponentUser
    }
    
];

@NgModule({
    declarations: [
        CompanyComponentUser,
        CompanyDetailComponentUser
    ],
    imports     : [
        RouterModule.forChild(CompanyRoutes),
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
        MatSortModule,
        MatGridListModule
    ]
})
export class CompanyModuleUser
{
}
