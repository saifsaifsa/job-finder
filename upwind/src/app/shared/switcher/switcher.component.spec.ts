import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SwitcherComponent } from './switcher.component';

describe('SwitcherComponent', () => {
  let component: SwitcherComponent;
  let fixture: ComponentFixture<SwitcherComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SwitcherComponent]
    });
    fixture = TestBed.createComponent(SwitcherComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
