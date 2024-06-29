import { Component, ViewEncapsulation, OnInit } from '@angular/core';
import { fuseAnimations } from '@fuse/animations';
import { AuthService } from 'app/core/auth/auth.service';
import { ActivatedRoute } from '@angular/router';

@Component({
    selector: 'auth-confirmation-required',
    templateUrl: './confirmation-required.component.html',
    encapsulation: ViewEncapsulation.None,
    animations: fuseAnimations
})
export class AuthConfirmationRequiredComponent implements OnInit {
    loading: boolean = false;
    token: string | null = null;
    message: string;

    constructor(
        private readonly _authService: AuthService,
        private readonly _activatedRoute: ActivatedRoute
    ) {}

    async ngOnInit(): Promise<void> {        
        this.loading = true;
        this.token = this._activatedRoute.snapshot.queryParamMap.get('token');
        
        if (this.token) {
                this._authService.confirmation(this.token).subscribe(
                    ()=>{
                        this.message = "Your account is now active.";
                    },
                (error)=>{
                    
                console.error('Confirmation failed:', error);
                this.message = 'Confirmation failed';
                })
            
        } else {
            this.message = 'No token provided';
        }
        
        this.loading = false;
    }
}
