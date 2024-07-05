import { Component, ViewEncapsulation, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { CvService } from '../Cv/CvService';
import { saveAs } from 'file-saver';
import { MatDialog } from '@angular/material/dialog';
import { CvPopupComponent } from '../cv-popup/cv-popup.component';
@Component({
  selector: 'app-cv',
  templateUrl: './cv.component.html',
  encapsulation: ViewEncapsulation.None
}) 
export class CvComponent implements OnInit {
  displayedColumns: string[] = ["Name", "Email", "Views", "Downloads", "Actions"];
  dataSource: MatTableDataSource<any>;
  @ViewChild(MatSort, { static: true }) sort: MatSort;
  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;

  constructor(private cvService: CvService, public dialog: MatDialog) {}
  ngOnInit() {
    this.loadCvs();
  }

  loadCvs() {
    this.cvService.getAllCvs().subscribe((data: any) => {
      this.dataSource = new MatTableDataSource(data);
      this.dataSource.sort = this.sort;
      this.dataSource.paginator = this.paginator;
    });
  }
  
  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value.trim().toLowerCase();
    this.dataSource.filter = filterValue;
  }

  deleteCv(cvId: number) {
    if (confirm('Are you sure you want to delete this CV?')) {
      this.cvService.deleteCv(cvId).subscribe(() => {
        this.loadCvs();
      });
    }
  }

  editCv(cvId: number) {
    // Implement edit logic here
  }
  downloadCv(id: number): void {
    this.cvService.exportCvToPDF(id).subscribe((blob) => {
      saveAs(blob, `cv-${id}.pdf`);
    });
  }
 
  openCv(id: number): void {
    this.cvService.getCv(id).subscribe(cv => {
      const dialogRef = this.dialog.open(CvPopupComponent, {
        width: '500px',
        data: cv
      });
  
      dialogRef.afterClosed().subscribe(result => {
        console.log('The dialog was closed');
      });
    });
  }
  
}
