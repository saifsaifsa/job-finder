import { Component, ViewEncapsulation, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { CvService } from '../Cv/CvService';
import { MatSnackBar } from '@angular/material/snack-bar';

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

  constructor(private cvService: CvService, private snackBar: MatSnackBar) { }

  ngOnInit() {
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

  deleteCv(cvId: number): void {
    if (confirm('Are you sure you want to delete this CV?')) {
      this.cvService.deleteCv(cvId).subscribe(() => {
        this.dataSource.data = this.dataSource.data.filter(cv => cv.id !== cvId);
        this.snackBar.open('CV deleted successfully', 'Close', { duration: 2000 });
      }, error => {
        console.error('Error deleting CV:', error);
        this.snackBar.open('Error deleting CV', 'Close', { duration: 2000 });
      });
    }
  }
}
