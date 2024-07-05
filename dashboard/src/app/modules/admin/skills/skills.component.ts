import { Component, ViewEncapsulation, OnInit, ViewChild } from '@angular/core';

import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

import { MatSort } from '@angular/material/sort';
import { SkillsService } from './skillsService';




@Component({
  selector: 'skills',
  templateUrl: './skills.component.html',
  encapsulation: ViewEncapsulation.None
})
export class SkillsComponent implements OnInit {
  displayedColumns: string[] = ["Label", "Category", "Actions"];
  dataSource: any;
  constructor(private SkillsService: SkillsService, public dialog: MatDialog) {
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
    // const dialogRef = this.dialog.open(AddCustomerComponent, {
    //   width: '640px',
    //   disableClose: true,
    //   data: mode === 'update' ? dataSource : null
    // });

    // dialogRef.afterClosed().subscribe(() => {
    //   this.refreshData();
    // });
  }

  public onDelete(id: any): void {
    this.SkillsService.deleteSkills(id).subscribe(() => {
      this.refreshData();
    }, error => {
      console.error(error);
    });
  }
  private refreshData(): void {
    this.SkillsService.getSkills().subscribe((data: any) => {
      this.dataSource = new MatTableDataSource(data);
      this.dataSource.sort = this.sort;
      this.dataSource.paginator = this.paginator;
    });
  }


}



