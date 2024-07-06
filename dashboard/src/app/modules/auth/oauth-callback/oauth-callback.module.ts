import { NgModule } from '@angular/core';
import { OauthCallbackComponent } from './oauth-callback.component';
import { RouterModule } from '@angular/router';
import { oauthCallbackRoutes } from './oauth-callback.routing';

@NgModule({
    declarations: [
        OauthCallbackComponent
    ],
    imports: [
        RouterModule.forChild(oauthCallbackRoutes),

    ]
})
export class OauthCallBackModule
{
}
