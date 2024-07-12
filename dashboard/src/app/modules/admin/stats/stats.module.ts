import { CUSTOM_ELEMENTS_SCHEMA, NgModule, NO_ERRORS_SCHEMA } from '@angular/core';
import { Route, RouterModule } from '@angular/router';
import { StatsComponentAdmin } from './stats.component';
import { CommonModule } from '@angular/common';
import { NgApexchartsModule } from 'ng-apexcharts';
import { NgxChartsModule } from '@swimlane/ngx-charts';

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
        CommonModule,
        NgApexchartsModule,
        NgxChartsModule,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA,NO_ERRORS_SCHEMA]
})
export class StatsModule
{
}
