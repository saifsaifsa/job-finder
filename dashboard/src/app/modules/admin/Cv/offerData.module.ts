import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatDividerModule } from '@angular/material/divider';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatMenuModule } from '@angular/material/menu';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';
import { MatTableModule } from '@angular/material/table';
import { MatTooltipModule } from '@angular/material/tooltip';
import { Route, RouterModule } from '@angular/router';
import { FuseCardModule } from '@fuse/components/card';
import { OfferComponent } from 'app/modules/admin/offer/offer.component';
import { SharedModule } from 'app/shared/shared.module';

const offerRoutes: Route[] = [
    {
        path     : '',
        component: OfferComponent
    }
];

@NgModule({
    declarations: [
        OfferComponent
    ],
    imports     : [
        RouterModule.forChild(offerRoutes),
        MatPaginatorModule,
        MatTableModule,
        MatCheckboxModule,
        MatButtonModule,
      
        CommonModule,
        MatButtonModule,
        MatButtonModule,
        MatCheckboxModule,
        MatDividerModule,
        MatFormFieldModule,
        MatIconModule,
        MatInputModule,
        MatMenuModule,
        MatTooltipModule,
        FuseCardModule,
        SharedModule,
        FormsModule,
        MatTableModule,
        MatCheckboxModule,
        MatPaginatorModule,
        MatSortModule,
        MatFormFieldModule,
        MatInputModule,

        ReactiveFormsModule


    ],

})
export class OfferModule
{
}
