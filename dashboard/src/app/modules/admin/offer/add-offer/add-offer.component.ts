import { Component, OnInit, VERSION, ViewChild } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { DeleteComponent } from '../delete-offer/delete.component';

@Component({
  selector: 'app-add-offre',
  templateUrl: './add-offre.component.html',
  styleUrls: []
})
export class AddCustomerComponent implements OnInit {
  public breakpoint: number; // Breakpoint observer code
  public fname: string = ``;
  public lname: string = `Suresh`;
  public addCusForm: FormGroup;
  wasFormChanged = false;

  constructor(
    private fb: FormBuilder,
    public dialog: MatDialog
  ) { }

  public ngOnInit(): void {
    
    this.addCusForm = this.fb.group({
     
      title: ['', [Validators.required, Validators.pattern('[a-zA-Z]+([a-zA-Z ]+)*')]],
      description: ['', [Validators.required, Validators.pattern('[a-zA-Z]+([a-zA-Z ]+)*')]],
      type: ['', [Validators.required, Validators.pattern('[a-zA-Z]+([a-zA-Z ]+)*')]],
      experience : ['', [Validators.required, Validators.pattern('[a-zA-Z]+([a-zA-Z ]+)*')]],
    
    });
    this.breakpoint = window.innerWidth <= 600 ? 1 : 2; 
  }

  public onAddCus(): void {
    if (this.addCusForm.valid) {
      console.log(this.addCusForm.value);
    }
  }

  openDialog(): void {
    console.log(this.wasFormChanged);
    if(this.addCusForm.dirty) {
      const dialogRef = this.dialog.open(DeleteComponent, {
        width: '340px',
      });
    } else {
      this.dialog.closeAll();
    }
  }

  // tslint:disable-next-line:no-any
  public onResize(event: any): void {
    this.breakpoint = event.target.innerWidth <= 600 ? 1 : 2;
  }

  private markAsDirty(group: FormGroup): void {
    group.markAsDirty();
    // tslint:disable-next-line:forin
    for (const i in group.controls) {
      group.controls[i].markAsDirty();
    }
  }

  formChanged() {
    this.wasFormChanged = true;
  }

}