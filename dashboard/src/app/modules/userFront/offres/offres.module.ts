import { NgModule } from '@angular/core';
import { Route, RouterModule } from '@angular/router';
import { OffresComponentAdmin } from './offres.component';
import { CommonModule } from '@angular/common';

const offresRoutes: Route[] = [
    {
        path     : '',
        component: OffresComponentAdmin
    }
];

@NgModule({
    declarations: [
        OffresComponentAdmin,
        
    ],
    imports     : [
        RouterModule.forChild(offresRoutes),CommonModule,
    ]
})
export class OffresModuleAdmin
{
}
