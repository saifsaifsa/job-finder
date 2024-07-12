import { Component, ViewEncapsulation } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { CompanyService } from 'app/core/company/companyService';
import { Company } from 'app/core/company/company.types';
import { fuseAnimations } from '@fuse/animations';
import { Offer } from 'app/core/company/offer.types';

@Component({
    selector: 'app-company',
    templateUrl: './company.component.html',
    encapsulation: ViewEncapsulation.None,
    animations: fuseAnimations,
})
export class CompanyComponentUser {
    companies: Company[] = [];
    companiesDataSource: MatTableDataSource<Company> = new MatTableDataSource();
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
    companyOffres: Offer[] = [];

    
    ngOnInit(): void{
        this.getAllCompanies();
    }
    /**
     * Constructor
     */
    constructor(private companyService: CompanyService, private dialog: MatDialog, private router: Router) {}


    onPageChange(event: any) {
        this.currentPage = event.pageIndex;
        this.pageSize = event.pageSize;
        this.getAllCompanies();
    }
    viewDetails(id: number): void {
        this.router.navigate(['/user/company', id]);
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
                this.companiesDataSource.data = data.content;
                this.companies = data;
                this.companies.forEach(train => {
                    this.photo = this.fileUrl+train.image;
                    train.image = this.photo;
                    if(train.offers.length>0) {
                        this.companyOffres=train.offers;
                    }
                });
                console.log(this.companies)
                console.log("this.companyOffres",this.companyOffres);

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
}
