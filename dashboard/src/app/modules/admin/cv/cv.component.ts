import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { CvService } from './cvService';
import { saveAs } from 'file-saver';
import { Router } from '@angular/router';

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

  constructor(private cvService: CvService, private router: Router) {}

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
    this.router.navigate(['/cv/edit', cvId]);
  }

  downloadCv(id: number): void {
    this.cvService.exportCvToPDF(id).subscribe((blob) => {
      saveAs(blob, `cv-${id}.pdf`);
      this.incrementDownloads(id); // Optionally update the downloads count in the UI
    });
  }

  openCv(id: number): void {
    this.cvService.getCv(id).subscribe((cv) => {
      this.selectedCv = cv;
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
}
