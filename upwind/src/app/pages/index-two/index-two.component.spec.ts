import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IndexTwoComponent } from './index-two.component';

describe('IndexTwoComponent', () => {
  let component: IndexTwoComponent;
  let fixture: ComponentFixture<IndexTwoComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [IndexTwoComponent]
    });
    fixture = TestBed.createComponent(IndexTwoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
