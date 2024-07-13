import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { Route, RouterModule } from '@angular/router';
import { OffresDetailComponentAdmin } from 'app/modules/userFront/offresDetail/offresDetail.component';
const offresDetailRoutes: Route[] = [
    {
        path     : '',
        component: OffresDetailComponentAdmin
    }
];

@NgModule({
    declarations: [
        OffresDetailComponentAdmin
    ],
    imports     : [
        RouterModule.forChild(offresDetailRoutes),
        CommonModule
    ]
})
export class OofresDetailModuleAdmin
{
}
