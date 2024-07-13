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
import { emailRegex } from 'app/layout/common/constants/regex';
import { emailRegexValidator, passwordRegexValidator } from 'app/layout/common/validators/validators';

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

    
    /**
     * On init
     */
    ngOnInit(): void {
        // Create the form
        this.signInForm = this._formBuilder.group({
            email: ['', [Validators.required, emailRegexValidator()]],
            password: ['', [Validators.required, passwordRegexValidator()]],
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
            (user: any) => {
                if (user.role[0].authority !== "ROLE_ADMIN") {
                this._router.navigateByUrl('/home');
                }
                if (user.role[0].authority !== "ROLE_USER") {
                this._router.navigateByUrl('/user');
                }
                if (user.role[0].authority !== "ROLE_PUBLISHER") {
                this._router.navigateByUrl('/publisher');
                }
            },
            (response) => {
                console.log("response.error.message",response.error.message);
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