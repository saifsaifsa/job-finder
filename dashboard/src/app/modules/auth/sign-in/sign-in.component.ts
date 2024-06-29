import { Component, OnInit, ViewChild, ViewEncapsulation } from '@angular/core';
import {
    AbstractControl,
    FormBuilder,
    FormGroup,
    NgForm,
    ValidatorFn,
    Validators,
} from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { fuseAnimations } from '@fuse/animations';
import { FuseAlertType } from '@fuse/components/alert';
import { AuthService } from 'app/core/auth/auth.service';

@Component({
    selector: 'auth-sign-in',
    templateUrl: './sign-in.component.html',
    encapsulation: ViewEncapsulation.None,
    animations: fuseAnimations,
})
export class AuthSignInComponent implements OnInit {
    @ViewChild('signInNgForm') signInNgForm: NgForm;

    alert: { type: FuseAlertType; message: string } = {
        type: 'success',
        message: '',
    };
    signInForm: FormGroup;
    showAlert: boolean = false;

    /**
     * Constructor
     */
    constructor(
        private _activatedRoute: ActivatedRoute,
        private _authService: AuthService,
        private _formBuilder: FormBuilder,
        private _router: Router
    ) {}

    // -----------------------------------------------------------------------------------------------------
    // @ Lifecycle hooks
    // -----------------------------------------------------------------------------------------------------

    emailRegexValidator(): ValidatorFn {
        const emailRegex: RegExp =
            /^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/;

        return (control: AbstractControl): { [key: string]: any } | null => {
            const email: string = control.value;

            if (email && !emailRegex.test(email)) {
                return { invalidEmailFormat: true };
            }

            return null;
        };
    }
    
    passwordRegexValidator(): ValidatorFn {
        const passwordRegex: RegExp =
            /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).{8,}$/;

        return (control: AbstractControl): { [key: string]: any } | null => {
            const password: string = control.value;

            if (password && !passwordRegex.test(password)) {
                return { invalidPasswordFormat: true };
            }

            return null;
        };
    }
    
    /**
     * On init
     */
    ngOnInit(): void {
        // Create the form
        this.signInForm = this._formBuilder.group({
            email: ['', [Validators.required, this.emailRegexValidator()]],
            password: ['', [Validators.required, this.passwordRegexValidator()]],
        });
    }

    // -----------------------------------------------------------------------------------------------------
    // @ Public methods
    // -----------------------------------------------------------------------------------------------------

    /**
     * Sign in
     */
    signIn(): void {
        // Return if the form is invalid
        if (this.signInForm.invalid) {
            return;
        }

        // Disable the form
        this.signInForm.disable();

        // Hide the alert
        this.showAlert = false;

        // Sign in
        this._authService.signIn(this.signInForm.value).subscribe(
            (res: any) => {
                this._router.navigateByUrl('/home');
            },
            (response) => {
                
                // Re-enable the form
                this.signInForm.enable();

                // Reset the form
                this.signInNgForm.resetForm();
                console.log(response);

                if (response == 'User role is not authorized.') {
                    this.alert = {
                        type: 'error',
                        message:
                            "You don't have permission to access",
                    };
                }else if (response.error.message == 'Le compte utilisateur est désactivé') {
                    this.alert = {
                        type: 'error',
                        message: 'You Account is not active',
                    };
                } else {
                    this.alert = {
                        type: 'error',
                        message: 'Wrong email or password',
                    };
                }
                this.showAlert = true;
            }
        );
    }
}