import { Directive, HostListener, inject } from '@angular/core';
import { ModalVideoComponent } from './modal-video/modal-video.component';
import { DialogService } from '@ngneat/dialog';

@Directive({
  selector: '[appVideopopup]'
})
export class VideopopupDirective {
  private dialog = inject(DialogService);

  constructor() { }
  @HostListener('click', ['$event']) onClick($event: any) {
    let element = $event.target.closest('[data-target]');
    this.dialog.open(ModalVideoComponent, {
      data: {
        title: element.dataset.target,
      },
    });
  }
}
