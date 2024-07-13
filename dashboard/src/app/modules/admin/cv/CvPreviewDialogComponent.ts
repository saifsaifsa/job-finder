import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-cv-preview-dialog',
  template: `
    <div class="p-6">
      <h2 class="text-lg font-semibold mb-4">{{ data.user.username }}</h2>
      <p class="text-gray-600 mb-2">{{ data.user.email }}</p>
      <p>{{ data.cvContent }}</p>
     
    </div>
  `
})
export class CvPreviewDialogComponent {
  constructor(@Inject(MAT_DIALOG_DATA) public data: any) {}
}
