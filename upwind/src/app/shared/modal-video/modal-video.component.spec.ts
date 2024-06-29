import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModalVideoComponent } from './modal-video.component';

describe('ModalVideoComponent', () => {
  let component: ModalVideoComponent;
  let fixture: ComponentFixture<ModalVideoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ModalVideoComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ModalVideoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
