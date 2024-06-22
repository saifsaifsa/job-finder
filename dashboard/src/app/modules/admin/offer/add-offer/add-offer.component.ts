import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialog } from '@angular/material/dialog';
import { DeleteComponent } from '../delete-offer/delete.component';
import { OfferService } from '../offerService';

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
    public offerService: OfferService,
    private fb: FormBuilder,
    public dialog: MatDialog,
    @Inject(MAT_DIALOG_DATA) public data: any // Inject data here

  ) { }

  public ngOnInit(): void {


    this.addCusForm = this.fb.group({

      title: [''],
      description: [''],
      type: [''],
      experienceLevel: [''],

    });



    // Wait for 2 seconds
    setTimeout(() => {

      if (this.isDataExist()) {
        console.log('Dialog opened with data:', this.data);
        // Populate the form with the data
        this.addCusForm.get('title').setValue(this.data.title);
        this.addCusForm.get('description').setValue(this.data.description);
        this.addCusForm.get('type').setValue(this.data.type);
        this.addCusForm.get('experienceLevel').setValue(this.data.experienceLevel);
      } else {
        console.log('Dialog opened without data');
      }
    }, 500);
    this.breakpoint = window.innerWidth <= 600 ? 1 : 2;
  }

  public onAddCus(): void {
    if (this.addCusForm.valid) {
      console.log(this.addCusForm.value);
    }
  }

  openDialog(): void {
    console.log(this.wasFormChanged);
    if (this.addCusForm.dirty) {
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

  isDataExist(): boolean {
    return this.data !== null && this.data !== undefined;
  }


  public onUpdate(): void {
    if (this.addCusForm.valid) {
      this.offerService.updateOffer(this.data.id, this.addCusForm.value).subscribe(() => {
        this.dialog.closeAll();

      }, error => {
        console.error(error);
      });
    }
  }

  public onadd(): void {
    if (this.addCusForm.valid) {
      this.offerService.addOffer( this.addCusForm.value).subscribe(() => {
        this.dialog.closeAll();

      }, error => {
        console.error(error);
      });
    }
  }


 
}