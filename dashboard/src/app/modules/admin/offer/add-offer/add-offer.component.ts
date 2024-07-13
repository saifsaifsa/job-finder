import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialog } from '@angular/material/dialog';
import { DeleteComponent } from '../delete-offer/delete.component';
import { OfferService } from '../offerService';
import { CompanyService } from 'app/core/company/companyService';
import { SkillsService } from '../../skills/skillsService';

@Component({
  selector: 'app-add-offre',
  templateUrl: './add-offre.component.html',
  styleUrls: []
})
export class AddCustomerComponent implements OnInit {
  public companysDataSource: { data: any[] } = { data: [] }; 
  
  public breakpoint: number;
  public fname: string = ``;
  public lname: string = `Suresh`;
  public addCusForm: FormGroup;
  wasFormChanged = false;
  public errorMessage: string = '';
  companys: any[] = [];
  public competences = []; // Example skills


  constructor(
    public offerService: OfferService,
    private fb: FormBuilder,
    public dialog: MatDialog,
    private companyService: CompanyService,
    private skillsService: SkillsService,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  public ngOnInit(): void {
    this.companyService.getAllCompanies().subscribe(
      (data: any) => {
        this.companys = data;
        console.log('Companies data: ', this.companys); // Log to verify data
      },
      (err) => {
        console.error('Error fetching companies: ', err);
      }
    );
    this.skillsService.getSkills().subscribe(
      (data: any) => {
        this.competences = data;
        console.log('Skills data: ', this.competences); // Log to verify data
      },
      (err) => {
        console.error('Error fetching skills: ', err);
      }
    );
  
  
    this.addCusForm = this.fb.group({
      title: [''],
      description: [''],
      type: [''],
      experienceLevel: [''],
      company: [this.companysDataSource],
      competences: this.competences
    });

    setTimeout(() => {
      if (this.isDataExist()) {
        console.log('Dialog opened with data:', this.data);
        this.addCusForm.patchValue(this.data);
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
    if (this.addCusForm.valid) {
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
    console.log(this.addCusForm.value);
    if (this.addCusForm.valid) {
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