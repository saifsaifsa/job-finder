import { Component, OnInit } from '@angular/core';
import { AuthService } from 'app/core/auth/auth.service';

@Component({
  selector: 'linkedin-oauth',
  templateUrl: './linkedin-oauth.component.html',
  styleUrls: ['./linkedin-oauth.component.scss']
})
export class LinkedinOauthComponent implements OnInit {

  constructor(private readonly _authService:AuthService) { }

  ngOnInit(): void {
  }
login(){
  this._authService.linkedInLogin()
}
}
