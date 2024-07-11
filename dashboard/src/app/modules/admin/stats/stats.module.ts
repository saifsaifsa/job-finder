import { NgModule } from '@angular/core';
import { Route, RouterModule } from '@angular/router';
import { ExampleComponentAdmin } from 'app/modules/admin/example/example.component';
import { StatsComponentAdmin } from './stats.component';
import { CommonModule } from '@angular/common';

const statsRoutes: Route[] = [
    {
        path     : '',
        component: StatsComponentAdmin
    }
];

@NgModule({
    declarations: [
        StatsComponentAdmin
    ],
    imports     : [
        RouterModule.forChild(statsRoutes),
        CommonModule
    ]
})
export class StatsModule
{
}
