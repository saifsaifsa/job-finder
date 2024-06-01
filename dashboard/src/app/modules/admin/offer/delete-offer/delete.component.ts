import { Component } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-delete',
  templateUrl: './delete.component.html',
  styleUrls: []
})
export class DeleteComponent {

constructor(private fb: FormBuilder,
private dialog: MatDialog,
              private dialogRef: MatDialogRef<DeleteComponent>) {} // Closing dialog window

public cancel(): void { // To cancel the dialog window
this.dialogRef.close();
}

public cancelN(): void { 
    this.dialog.closeAll();
}

}