import { Component, ViewEncapsulation } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { TrainingService } from 'app/core/training/trainingService';
import { Training } from 'app/core/training/training.types';
import { TrainingCategories } from 'app/core/training/training.enums';
import { fuseAnimations } from '@fuse/animations';

@Component({
    selector: 'app-training',
    templateUrl: './training.component.html',
    encapsulation: ViewEncapsulation.None,
    animations: fuseAnimations,
})
export class TrainingComponentUser {
    trainings: Training[] = [];
    trainingsDataSource: MatTableDataSource<Training> = new MatTableDataSource();
    recentTransactionsTableColumns: string[] = ['title', 'trainingCategories', 'rating', 'price', 'dateDebut',  "actions"];
    trainingCategories = Object.values(TrainingCategories);
    sortOrder: 'asc' | 'desc' = 'asc';
    pageSize: number = 6;
    currentPage: number = 0;
    totalItems:number=0
    sortField: string = '';
    filterValues: { [key: string]: string } = {
        price: '',
        likes: '',
        dislikes: '',
        rating: '',
    };
    private fileUrl = 'http://localhost:8080/api/files/';
    photo: string | ArrayBuffer | null = null;
    
    ngOnInit(): void{
        this.getAllTrainings();
    }
    /**
     * Constructor
     */
    constructor(private trainingService: TrainingService, private dialog: MatDialog, private router: Router) {}

    onTrainingCategoryChange(selectedCategory: string): void {
        if (selectedCategory == 'None') {
            this.trainingService.getTrainings().subscribe(res => {
                console.log(res);
                this.trainingsDataSource.data = res;
            });
        } else {
            this.trainingService.getFilteredTrainings(selectedCategory).subscribe(res => {
                this.trainingsDataSource.data = res;
            })
        }
      }
    onPageChange(event: any) {
        this.currentPage = event.pageIndex;
        this.pageSize = event.pageSize;
        this.getAllTrainings();
    }
    viewDetails(id: number): void {
        this.router.navigate(['/user/training', id]);
      }
    getAllTrainings() {
        this.trainingService.getAllTrainings(
            this.currentPage,
            this.pageSize,
            this.filterValues,
            this.sortField,
            this.sortOrder
        ).subscribe(
            (data: any) => {
                this.trainingsDataSource.data = data.content;
                this.trainings = data.content;
                this.trainings.forEach(train => {
                    this.photo = this.fileUrl+train.image;
                    train.image = this.photo;
                });
                console.log(this.trainings)
                this.totalItems = data.totalElements
            },
            (err) => {
                console.log('errors: ', err);
            });
    }
    trackByFn(index: number, item: any): any {
        return item.id || index;
    }
    reload() {
        this.reloadComponent(false, 'training');
    }
    applySort(sort: { active: string; direction: 'asc' | 'desc' }) {
        this.sortField = sort.active;
        this.sortOrder = sort.direction;
        this.getAllTrainings();
    }
    applyFilters() {
        this.currentPage = 0;
        this.getAllTrainings();
    }
    reloadCurrent() {
        this.reloadComponent(true);
    }
    reloadComponent(self: boolean, urlToNavigateTo?: string) {
        //skipLocationChange:true means dont update the url to / when navigating
        console.log("Current route I am on:", this.router.url);
        const url = self ? this.router.url : urlToNavigateTo;
        this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
            this.router.navigate([`/${url}`]).then(() => {
                console.log(`After navigation I am on:${this.router.url}`)
            })
        })
    }

    reloadPage() {
        window.location.reload()
    }
}
