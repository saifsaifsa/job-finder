import { Route } from '@angular/router';
import { AuthResetPasswordComponent } from 'app/modules/auth/reset-password/reset-password.component';
import { OauthCallbackComponent } from './oauth-callback.component';

export const oauthCallbackRoutes: Route[] = [
    {
        path     : '',
        component: OauthCallbackComponent
    }
];
