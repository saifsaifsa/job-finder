import { NgModule } from '@angular/core';
import { Route, RouterModule } from '@angular/router';
import { ExampleComponentUser } from 'app/modules/userFront/example/example.component';

const exampleRoutes: Route[] = [
    {
        path     : '',
        component: ExampleComponentUser
    }
];

@NgModule({
    declarations: [
        ExampleComponentUser
    ],
    imports     : [
        RouterModule.forChild(exampleRoutes)
    ]
})
export class ExampleModule
{
}
