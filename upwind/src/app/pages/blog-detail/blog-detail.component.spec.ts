import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BlogdetailComponent } from './blog-detail.component';

describe('BlogdetailComponent', () => {
  let component: BlogdetailComponent;
  let fixture: ComponentFixture<BlogdetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BlogdetailComponent]
    });
    fixture = TestBed.createComponent(BlogdetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
