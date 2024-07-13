import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialog } from '@angular/material/dialog';
import { DeleteComponent } from '../delete-offer/delete.component';
import { OfferService } from '../offerService';
import { MatSort } from '@angular/material/sort';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';

@Component({
  selector: 'app-get-profiles',
  templateUrl: './get-profiles.component.html',
  styleUrls: []
})
export class GetProfilesComponent implements OnInit {
  displayedColumns: string[] = ["Name", "Prename", "Mail", "ExperienceLevel", "Actions"];
  dataSource: any;

  @ViewChild(MatSort, { static: true }) sort: MatSort;
  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value.trim().toLowerCase();
    this.dataSource.filter = filterValue;
  }

  private refreshData(): void {
    const data = [
      { Name: 'John', Prename: 'Doe', Mail: 'john.doe@example.com', ExperienceLevel: 'Senior' },
      { Name: 'Jane', Prename: 'Doe', Mail: 'jane.doe@example.com', ExperienceLevel: 'Junior' },
      { Name: 'Alice', Prename: 'Smith', Mail: 'alice.smith@example.com', ExperienceLevel: 'Mid' },
    ];
    console.log(data);

    this.dataSource = new MatTableDataSource(data);
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
  }

  public breakpoint: number;
  public fname: string = ``;
  public lname: string = `Suresh`;
  public addCusForm: FormGroup;
  wasFormChanged = false;
  public errorMessage: string = '';

  constructor(
    public offerService: OfferService,
    private fb: FormBuilder,
    public dialog: MatDialog,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {}

  public ngOnInit(): void {
    this.refreshData();

    this.addCusForm = this.fb.group({
      title: [''],
      description: [''],
      type: [''],
      experienceLevel: [''],
    });

    setTimeout(() => {
      if (this.isDataExist()) {
        console.log('Dialog opened with data:', this.data);
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
      this.dialog.open(DeleteComponent, {
        width: '340px',
      });
    } else {
      this.dialog.closeAll();
    }
  }

  public onResize(event: any): void {
    this.breakpoint = event.target.innerWidth <= 600 ? 1 : 2;
  }

  private markAsDirty(group: FormGroup): void {
    group.markAsDirty();
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
    if (this.addCusForm.valid && this.addCusForm.value.title && this.addCusForm.value.description && this.addCusForm.value.type && this.addCusForm.value.experienceLevel) {
      this.offerService.updateOffer(this.data.id, this.addCusForm.value).subscribe(() => {
        this.dialog.closeAll();
      }, error => {
        console.error(error);
        this.errorMessage = 'An error occurred while updating the offer.';
      });
    } else {
      this.errorMessage = 'Form is not valid or some fields are empty';
    }
  }

  public onadd(): void {
    if (this.addCusForm.valid && this.addCusForm.value.title && this.addCusForm.value.description && this.addCusForm.value.type && this.addCusForm.value.experienceLevel) {
      this.offerService.addOffer(this.addCusForm.value).subscribe(() => {
        this.dialog.closeAll();
      }, error => {
        console.error(error);
        this.errorMessage = 'An error occurred while adding the offer.';
      });
    } else {
      this.errorMessage = 'Form is not valid or some fields are empty';
    }
  }
}
