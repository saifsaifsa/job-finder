import { Component, ViewEncapsulation, OnInit, ViewChild } from '@angular/core';

import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { MatDialog,MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

import {MatSort} from '@angular/material/sort';
import { OfferService } from './offerService';
import { AddCustomerComponent } from './add-offer/add-offer.component';




@Component({
    selector     : 'offer',
    templateUrl  : './offer.component.html',
    encapsulation: ViewEncapsulation.None
})
export class OfferComponent implements OnInit
{
    displayedColumns: string[] = ["Title","Description", "Type","Experience Level"];
    dataSource : any;
    constructor(private offerService:OfferService,public dialog: MatDialog) {
    }

    @ViewChild(MatSort, {static: true}) sort: MatSort;
    @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  
    ngOnInit() {
      // get data from service

      this.offerService.getOffer().subscribe((data: any) => {
        console.log(data);
        this.dataSource = new MatTableDataSource(data);
        this.dataSource.sort = this.sort;
        this.dataSource.paginator = this.paginator;
      });
    }
    applyFilter(event: Event) {
      const filterValue = (event.target as HTMLInputElement).value.trim().toLowerCase();
      this.dataSource.filter = filterValue;

     
     
    }
    openDialog(): void {
      const dialogRef = this.dialog.open(AddCustomerComponent,{
        width: '640px',disableClose: true 
      });
  }
   
  }
  
  
  
