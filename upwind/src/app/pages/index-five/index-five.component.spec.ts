import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IndexFiveComponent } from './index-five.component';

describe('IndexFiveComponent', () => {
  let component: IndexFiveComponent;
  let fixture: ComponentFixture<IndexFiveComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [IndexFiveComponent]
    });
    fixture = TestBed.createComponent(IndexFiveComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
