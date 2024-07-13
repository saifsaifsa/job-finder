import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http'; 
import { CvListComponent } from './cv-list.component';
import { CvDetailComponent } from './cv-detail.component';
import { CvUploadComponent } from './cv-upload.component';
import { CvGenerateComponent } from './cv-generate.component';

@NgModule({
  declarations: [
    CvListComponent,
    CvDetailComponent,
    CvGenerateComponent,
    CvUploadComponent 
  ],
  imports: [
    CommonModule,
    FormsModule,
    HttpClientModule, // Add HttpClientModule
    RouterModule.forChild([
      { path: '', component: CvListComponent },
      { path: 'cv/:id', component: CvDetailComponent },
      { path: 'generate', component: CvGenerateComponent },
      { path: 'upload', component: CvUploadComponent }
    ])
  ]
})
export class CvModuleUser { }
