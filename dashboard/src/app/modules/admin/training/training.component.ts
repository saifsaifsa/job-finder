import { Component, ViewEncapsulation } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { TrainingService } from 'app/core/training/trainingService';
import { Training } from 'app/core/training/training.types';
import { TrainingCategories } from 'app/core/training/training.enums';

@Component({
    selector: 'app-training',
    templateUrl: './training.component.html',
    encapsulation: ViewEncapsulation.None
})
export class TrainingComponent {
    trainingsDataSource: MatTableDataSource<Training> = new MatTableDataSource();
    recentTransactionsTableColumns: string[] = ['title', 'trainingCategories', 'rating', 'price', 'dateDebut',  "actions"];
    trainingCategories = Object.values(TrainingCategories);
    routerLink: string[] = ['/training', ''];
    /**
     * Constructor
     */
    constructor(private trainingService: TrainingService, private router: Router) {
        this.trainingService.getTrainings().subscribe(res => {
            console.log(res);
            this.trainingsDataSource.data = res;
        })
    }

    onTrainingCategoryChange(selectedCategory: string): void {
        if (selectedCategory == 'None') {
            this.trainingService.getTrainings().subscribe(res => {
                console.log(res);
                this.trainingsDataSource.data = res;
            });
        } else {
            this.trainingService.getFilteredTrainings(selectedCategory).subscribe(res => {
                console.log(res);
                this.trainingsDataSource.data = res;
            })
        }
      }
    trackByFn(index: number, item: any): any {
        return item.id || index;
    }
    reload() {
        this.reloadComponent(false, 'training');
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
    deleteTraining(trainingId: string): void {
        console.log(trainingId);
        this.trainingService.deleteTraining(trainingId).subscribe(
            () => {
                this.reload()
            },
            (error) => {
                console.log(error);
            }

        );
    }
}
