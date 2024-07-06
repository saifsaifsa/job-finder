import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IndexEightComponent } from './index-eight.component';

describe('IndexEightComponent', () => {
  let component: IndexEightComponent;
  let fixture: ComponentFixture<IndexEightComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [IndexEightComponent]
    });
    fixture = TestBed.createComponent(IndexEightComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
