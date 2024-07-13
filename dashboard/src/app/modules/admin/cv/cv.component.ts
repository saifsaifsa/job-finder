import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { CvService } from './cvService';
import { Router } from '@angular/router';
import { EditCvDialogComponent } from './EditCvDialogComponent';
import { MatDialog } from '@angular/material/dialog';
import { saveAs } from 'file-saver';
import { CvPreviewDialogComponent } from './CvPreviewDialogComponent';

@Component({
  selector: 'app-cv',
  templateUrl: './cv.component.html',
  styleUrls: ['./cv.component.scss']
})
export class CvComponent implements OnInit {
  displayedColumns: string[] = ["Name", "Email", "UserName", "Views", "Downloads", "Actions"];
  dataSource: MatTableDataSource<any>;
  selectedCv: any = null;

  @ViewChild(MatSort, { static: true }) sort: MatSort;
  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;

  constructor(private cvService: CvService, private router: Router, private dialog: MatDialog) {}

  ngOnInit() {
    this.loadCvs();
  }

  loadCvs() {
    this.cvService.getAllCvs().subscribe(
      (data: any) => {
        console.log('Received CV data:', data);
        this.dataSource = new MatTableDataSource(data);
        this.dataSource.sort = this.sort;
        this.dataSource.paginator = this.paginator;
      }
    );
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value.trim().toLowerCase();
    this.dataSource.filter = filterValue;
  }

  deleteCv(cvId: number) {
    if (confirm('Are you sure you want to delete this CV?')) {
      this.cvService.deleteCv(cvId).subscribe(() => {
        this.loadCvs();
        alert('CV deleted successfully.');
      },
      (error) => {
        console.error('Error deleting CV:', error);
        alert('There was an error deleting the CV. Please try again later.');
      });
    }
  }

  
  editCv(cv: any): void {
    const dialogRef = this.dialog.open(EditCvDialogComponent, {
      width: '400px',
      data: cv
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        // Handle the result (e.g., save the changes)
        console.log('Dialog result:', result);
        // Call the service to update the CV details
        this.cvService.updateCv(result).subscribe(() => {
          this.loadCvs(); // Reload the CV list
        });
      }
    });
  }
  downloadCv(id: number): void {
    this.cvService.exportCvToPDF(id).subscribe(
      (blob) => {
        saveAs(blob, `cv-${id}.pdf`);
        this.incrementDownloads(id);
        alert('CV downloaded successfully.');
      }
    );
  }

  openCv(cv: any): void {
    this.cvService.getCv(cv.id).subscribe((cvData) => {
      this.selectedCv = cvData;
      
      // Increment views
      this.cvService.incrementViews(cv.id).subscribe(() => {
        console.log('Views incremented for CV ID:', cv.id);
        this.incrementViews(cv.id); // Update the view count in the table
      });

      // Optionally, open a preview dialog or modal
      const dialogRef = this.dialog.open(CvPreviewDialogComponent, {
        width: '800px', // Adjust width as needed
        data: cvData
      });

      dialogRef.afterClosed().subscribe(() => {
        // Optional: Handle any close actions if needed
        this.selectedCv = null; // Reset selectedCv when dialog/modal is closed
      });
    });
  }  

  closeCv(): void {
    this.selectedCv = null;
  }

  private incrementDownloads(id: number): void {
    const cvToUpdate = this.dataSource.data.find(cv => cv.id === id);
    if (cvToUpdate) {
      cvToUpdate.downloads++;
      this.dataSource._updateChangeSubscription();
    }
  }
  private incrementViews(id: number): void {
    const cvToUpdate = this.dataSource.data.find(cv => cv.id === id);
    if (cvToUpdate) {
      cvToUpdate.views++;
      this.dataSource._updateChangeSubscription();
    }
  }
}
