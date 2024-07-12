import { NgModule } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogModule } from '@angular/material/dialog';
import { MatTableModule } from '@angular/material/table';
import { Route, RouterModule } from '@angular/router';
import { CompanyComponent } from 'app/modules/admin/company/company.component';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { FuseAlertModule } from '@fuse/components/alert';
import { CompanyDetailComponent } from './companyDetail.component';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';
const CompanyRoutes: Route[] = [
    {
        
        path     : '',
        component: CompanyComponent
    },
    {
        path     : 'add',
        component: CompanyDetailComponent
    },
    {
        path     : 'update/:id',
        component: CompanyDetailComponent
    }
];

@NgModule({
    declarations: [
        CompanyComponent,
        CompanyDetailComponent
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
        MatSortModule
    ]
})
export class CompanyModule
{
}
