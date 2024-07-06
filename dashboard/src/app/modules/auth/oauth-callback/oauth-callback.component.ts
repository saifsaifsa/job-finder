
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
    selector: 'app-callback',
    template: '<p>{{state}}<p>'
})
export class OauthCallbackComponent implements OnInit {

    constructor(private router: Router) {}
    code:String
    state:String
    ngOnInit() {
        
        const urlParams = new URLSearchParams(window.location.search);
         this.code = urlParams.get('code');
         this.state = urlParams.get('state');
        // Validate state to prevent CSRF attacks
        if (this.state === 'random_string') {
            window.opener.postMessage({ code: this.code }, window.location.origin);
            window.close();
        } else {
            // Handle invalid state
            window.close();
        }
    }
}
