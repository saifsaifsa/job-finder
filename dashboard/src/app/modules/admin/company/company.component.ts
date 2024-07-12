import { Component, ViewEncapsulation } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { CompanyService } from 'app/core/company/companyService';
import { Company } from 'app/core/company/company.types';
import { fuseAnimations } from '@fuse/animations';

@Component({
    selector: 'app-company',
    templateUrl: './company.component.html',
    encapsulation: ViewEncapsulation.None,
    animations: fuseAnimations,
})
export class CompanyComponent {
    companysDataSource: MatTableDataSource<Company> = new MatTableDataSource();
    recentTransactionsTableColumns: string[] = ['name', 'industry', 'rating', 'location', 'description', "actions"];
    sortOrder: 'asc' | 'desc' = 'asc';
    pageSize: number = 10;
    currentPage: number = 0;
    totalItems:number=0
    sortField: string = '';
    filterValues: { [key: string]: string } = {
        rating: '',
    };

    ngOnInit(): void{
        this.getAllCompanies();
    }
    /**
     * Constructor
     */
    constructor(private companyService: CompanyService, private dialog: MatDialog, private router: Router) {}

    // onTrainingCategoryChange(selectedCategory: string): void {
    //     if (selectedCategory == 'None') {
    //         this.companyService.getCompanies().subscribe(res => {
    //             console.log(res);
    //             this.companysDataSource.data = res;
    //         });
    //     } else {
    //         this.trainingService.getFilteredTrainings(selectedCategory).subscribe(res => {
    //             this.trainingsDataSource.data = res;
    //         })
    //     }
    //   }
    onPageChange(event: any) {
        this.currentPage = event.pageIndex;
        this.pageSize = event.pageSize;
        this.getAllCompanies();
    }

    getAllCompanies() {
        this.companyService.getAllCompanies(
            this.currentPage,
            this.pageSize,
            this.filterValues,
            this.sortField,
            this.sortOrder
        ).subscribe(
            (data: any) => {
                this.companysDataSource.data = data;
                this.totalItems = data.totalElements
                console.log('data: ', data);
            },
            (err) => {
                console.log('errors: ', err);
            });
    }
    trackByFn(index: number, item: any): any {
        return item.id || index;
    }
    reload() {
        this.reloadComponent(false, 'company');
    }
    applySort(sort: { active: string; direction: 'asc' | 'desc' }) {
        this.sortField = sort.active;
        this.sortOrder = sort.direction;
        this.getAllCompanies();
    }
    applyFilters() {
        this.currentPage = 0;
        this.getAllCompanies();
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
    deleteCompany(id: string): void {
        console.log(id);
        this.companyService.deleteCompany(id).subscribe(
            () => {
                this.reload()
            },
            (error) => {
                console.log(error);
            }

        );
    }
}
