import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'app/core/auth/auth.service';

@Component({
  selector: 'linkedin-oauth',
  templateUrl: './linkedin-oauth.component.html',
  styleUrls: ['./linkedin-oauth.component.scss']
})
export class LinkedinOauthComponent implements OnInit {

  constructor(private readonly _authService:AuthService, private _router: Router  ) { }

  ngOnInit(): void {
  }
linkedInAuth(){
  this._authService.linkedInLogin().subscribe(
    (user: any) => {
      console.log("user after linkedinAuth:",user);
      
      this._router.navigateByUrl('/');
      
      console.log("after redirection");
        // if (user.role[0].authority !== "ROLE_ADMIN") {
        // this._router.navigateByUrl('/home');
        // }
        // if (user.role[0].authority !== "ROLE_USER") {
        // this._router.navigateByUrl('/user');
        // }
        // if (user.role[0].authority !== "ROLE_PUBLISHER") {
        // this._router.navigateByUrl('/publisher');
        // }
    })
}
}
