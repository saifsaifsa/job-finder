import { Route } from '@angular/router';
import { InitialDataResolver } from 'app/app.resolvers';
import { AuthGuard } from 'app/core/auth/guards/auth.guard';
import { NoAuthGuard } from 'app/core/auth/guards/noAuth.guard';
import { LayoutComponent } from 'app/layout/layout.component';

// @formatter:off
/* eslint-disable max-len */
/* eslint-disable @typescript-eslint/explicit-function-return-type */
export const appRoutes: Route[] = [
    // Redirect empty path to '/example'
    { path: '', pathMatch: 'full', redirectTo: '/home' },

    // Redirect signed in user to the '/example'
    //
    // After the user signs in, the sign in page will redirect the user to the 'signed-in-redirect'
    // path. Below is another redirection for that path to redirect the user to the desired
    // location. This is a small convenience to keep all main routes together here on this file.
    { path: 'signed-in-redirect', pathMatch: 'full', redirectTo: 'example' },

    // Auth routes for guests
    {
        path: '',
        canActivate: [NoAuthGuard],
        canActivateChild: [NoAuthGuard],
        component: LayoutComponent,
        data: {
            layout: 'empty',
        },
        children: [
            {
                path: 'confirmation',
                loadChildren: () =>
                    import(
                        'app/modules/auth/confirmation-required/confirmation-required.module'
                    ).then((m) => m.AuthConfirmationRequiredModule),
            },
            {
                path: 'forgot-password',
                loadChildren: () =>
                    import(
                        'app/modules/auth/forgot-password/forgot-password.module'
                    ).then((m) => m.AuthForgotPasswordModule),
            },
            {
                path: 'reset-password',
                loadChildren: () =>
                    import(
                        'app/modules/auth/reset-password/reset-password.module'
                    ).then((m) => m.AuthResetPasswordModule),
            },
            {
                path: 'sign-in',
                loadChildren: () =>
                    import('app/modules/auth/sign-in/sign-in.module').then(
                        (m) => m.AuthSignInModule
                    ),
            },
            {
                path: 'sign-up',
                loadChildren: () =>
                    import('app/modules/auth/sign-up/sign-up.module').then(
                        (m) => m.AuthSignUpModule
                    ),
            },
            {
                path: 'callback',
                loadChildren: () =>
                    import('app/modules/auth/oauth-callback/oauth-callback.module').then(
                        (m) => m.OauthCallBackModule
                    ),
            },
        ],
    },

    // Auth routes for authenticated users
    {
        path: '',
        canActivate: [AuthGuard],
        canActivateChild: [AuthGuard],
        component: LayoutComponent,
        data: {
            layout: 'empty',
        },
        children: [
            {
                path: 'sign-out',
                loadChildren: () =>
                    import('app/modules/auth/sign-out/sign-out.module').then(
                        (m) => m.AuthSignOutModule
                    ),
            },
            {
                path: 'unlock-session',
                loadChildren: () =>
                    import(
                        'app/modules/auth/unlock-session/unlock-session.module'
                    ).then((m) => m.AuthUnlockSessionModule),
            },
        ],
    },

    // Landing routes
    // {
    //     path: '',
    //     component  : LayoutComponent,
    //     data: {
    //         layout: 'empty'
    //     },
    //     children   : [
    //         {path: 'home', loadChildren: () => import('app/modules/landing/home/home.module').then(m => m.LandingHomeModule)},
    //     ]
    // },

    // Admin routes
    // TODO check if has role ROLE_ADMIN
    {
        path: '',
        canActivate: [AuthGuard],
        canActivateChild: [AuthGuard],
        component: LayoutComponent,
        resolve: {
            initialData: InitialDataResolver,
        },
        children: [
            {path: 'home',loadChildren: () => import('app/modules/admin/example/example.module').then((m) => m.ExampleModuleAdmin),},
            {path: 'dashboard',loadChildren: () => import('app/modules/admin/stats/stats.module').then((m) => m.StatsModule),},
            {path: 'users',loadChildren: () =>import('app/modules/admin/user/user.module').then((m) => m.UserModule),},
            {path: 'offre', loadChildren: () => import('app/modules/admin/offer/offerData.module').then(m => m.OfferModule)},
            {path: 'company', loadChildren: () => import('app/modules/admin/company/company.module').then(m => m.CompanyModule)},
            {path: 'training', loadChildren: () => import('app/modules/admin/training/training.module').then((m) => m.TrainingModule)},
            {path: 'skills', loadChildren: () => import('app/modules/admin/skills/skills.module').then((m) => m.SkillsModule)},
            {path: 'quizz', loadChildren: () => import('app/modules/admin/quizz/quizz.module').then((m) => m.QuizzModule)},           
            {path: 'cv', loadChildren: () => import('app/modules/admin/cv/cv.module').then((m) => m.CvModule)},       
       
        ],
    },
    // Publisher routes
    // TODO check if has ROLE_PUBLISHER
    {
        path: 'publisher',
        canActivate: [AuthGuard],
        canActivateChild: [AuthGuard],
        component: LayoutComponent,
        resolve: {
            initialData: InitialDataResolver,
        },
        children: [
            {path: 'exemple1',loadChildren: () => import('app/modules/publisherFront/example/example.module').then((m) => m.ExampleModule),},
            {path: 'training', loadChildren: () => import('app/modules/admin/training/training.module').then((m) => m.TrainingModule)},
            {path: 'company', loadChildren: () => import('app/modules/admin/company/company.module').then((m) => m.CompanyModule)},

        ],
    },
    // User student routes
    // TODO check if has ROLE_PUBLISHER
    {
        path: 'user',
        canActivate: [AuthGuard],
        canActivateChild: [AuthGuard],
        component: LayoutComponent,
        resolve: {
            initialData: InitialDataResolver,
        },
        children: [
            {path: 'exemple2',loadChildren: () => import('app/modules/userFront/example/example.module').then((m) => m.ExampleModuleUser),},
            {path: 'training',loadChildren: () => import('app/modules/userFront/training/training.module').then((m) => m.TrainingModuleUser),},
            { path: 'success',loadChildren: () => import('app/modules/userFront/success-page/success-page.module').then((m) => m.SuccessModule), },
            { path: 'cancel',loadChildren: () => import('app/modules/userFront/cancel-page/cancel-page.module').then((m) => m.CancelModule), },
        ],
    },
];
