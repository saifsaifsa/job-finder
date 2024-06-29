import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PortofolioDetailComponent } from './portfolio-detail.component';

describe('PortofolioDetailComponent', () => {
  let component: PortofolioDetailComponent;
  let fixture: ComponentFixture<PortofolioDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PortofolioDetailComponent]
    });
    fixture = TestBed.createComponent(PortofolioDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
