import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-confirm-dialog-commande',
  templateUrl: './confirm-dialog-commande.component.html'
})
export class ConfirmDialogCommandeComponent {
  constructor(
    public dialogRef: MatDialogRef<ConfirmDialogCommandeComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {}

  confirm(): void {
    this.dialogRef.close(true); // Return 'true' to indicate the user confirmed the deletion
  }

  cancel(): void {
    this.dialogRef.close(false); // Return 'false' to indicate the user canceled the deletion
  }
}