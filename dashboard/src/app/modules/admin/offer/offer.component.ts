import { Component, ViewEncapsulation, OnInit, ViewChild } from '@angular/core';

import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

import { MatSort } from '@angular/material/sort';
import { OfferService } from './offerService';
import { AddCustomerComponent } from './add-offer/add-offer.component';
import { GetProfilesComponent } from './get-profiles/get-profiles.component';




@Component({
  selector: 'offer',
  templateUrl: './offer.component.html',
  encapsulation: ViewEncapsulation.None
})
export class OfferComponent implements OnInit {
  displayedColumns: string[] = ["NameCompany","LogoCompany", "Title", "Description", "Type", "Experience Level", "Actions"];
  dataSource: any;
  constructor(private offerService: OfferService, public dialog: MatDialog) {
  }

  @ViewChild(MatSort, { static: true }) sort: MatSort;
  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;

  ngOnInit() {


    this.refreshData();
  }
  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value.trim().toLowerCase();
    this.dataSource.filter = filterValue;



  }
  openDialog(mode: 'add' | 'update', dataSource?: any): void {
    const dialogRef = this.dialog.open(AddCustomerComponent, {
      width: '640px',
      disableClose: true,
      data: mode === 'update' ? dataSource : null
    });

    dialogRef.afterClosed().subscribe(() => {
      this.refreshData();
    });
  }

  public onDelete(id: any): void {
    this.offerService.deleteOffer(id).subscribe(() => {
      this.refreshData();
    }, error => {
      console.error(error);
    });
  }
  private refreshData(): void {
    this.offerService.getOffer().subscribe((data: any) => {
      this.dataSource = new MatTableDataSource(data);
      this.dataSource.sort = this.sort;
      this.dataSource.paginator = this.paginator;
    });
  }

  getTopProfiles(): void {
    const dialogRef = this.dialog.open(GetProfilesComponent, {
      width: '1280px',
      disableClose: true,
      data:  null
    });



}
}



