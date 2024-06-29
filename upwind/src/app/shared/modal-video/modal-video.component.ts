import { Component, inject } from '@angular/core';
import { DialogRef } from '@ngneat/dialog';
interface Data {
  title: string
}
@Component({
  selector: 'app-modal-video',
  templateUrl: './modal-video.component.html',
  styleUrls: ['./modal-video.component.scss']
})
export class ModalVideoComponent {
  ref: DialogRef<Data, boolean> = inject(DialogRef);
  src: any;

  ngOnInit() {
    this.src = this.ref.data.title;
  }

  get title() {
    if (!this.ref.data) return 'https://www.youtube.com/embed/S_CGed6E610';
    return this.ref.data.title;
  }
}
