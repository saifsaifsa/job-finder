import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LinkedinOauthComponent } from './linkedin-oauth.component';

describe('LinkedinOauthComponent', () => {
  let component: LinkedinOauthComponent;
  let fixture: ComponentFixture<LinkedinOauthComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LinkedinOauthComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LinkedinOauthComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
