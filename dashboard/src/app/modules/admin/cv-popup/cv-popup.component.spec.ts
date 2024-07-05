import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CvPopupComponent } from './cv-popup.component';

describe('CvPopupComponent', () => {
  let component: CvPopupComponent;
  let fixture: ComponentFixture<CvPopupComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CvPopupComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CvPopupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
