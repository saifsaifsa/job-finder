import { Component, OnInit, ViewChild, ViewEncapsulation } from "@angular/core";
import { FormControl, FormGroup, NgForm, Validators } from "@angular/forms";
import { ActivatedRoute, Router } from "@angular/router";
import { fuseAnimations } from "@fuse/animations";
import { FuseAlertType } from "@fuse/components/alert";
import { DatePipe } from '@angular/common';
import { CompanyService } from "app/core/company/companyService";
import { Company } from "app/core/company/company.types";
import { PaymentService } from "app/core/payment/paymentService";
import { HttpHeaders } from "@angular/common/http";

@Component({
    selector: 'app-company-detail',
    templateUrl: './companyDetail.component.html',
    encapsulation: ViewEncapsulation.None,
    animations: fuseAnimations,
    providers: [DatePipe]
})
export class CompanyDetailComponentUser implements OnInit {
    @ViewChild('CompanyDetailsNgForm') companyDetailsNgForm: NgForm;
    private fileUrl = 'http://localhost:8080/api/files/';
    photo: string | ArrayBuffer | null = null;
    paymentUrl: string = '';

    alert: { type: FuseAlertType; message: string } = {
        type: 'success',
        message: '',
    };
    showAlert: boolean = false;
    companyDetailsForm: FormGroup;
    isUpdating: boolean = false;
    company:Company;
    constructor(
        private companyService: CompanyService,
        private router: Router,
        private route: ActivatedRoute,
        private datePipe: DatePipe, 
        private paymentService: PaymentService
    ) { }
    private formatDate(date: Date): string {
        console.log("date:",date);
        console.log("this.datePipe.transform(date, 'yyyy/MM/dd')",this.datePipe.transform(date, 'yyyy/MM/dd'));
        
        return this.datePipe.transform(date, 'yyyy/MM/dd') || '';
      }
    ngOnInit(): void {
        this.route.params.subscribe((params) => {
            const companyId = params['id'];
                this.companyService.getCompany(companyId).subscribe((train: any) => {
                    this.photo = this.fileUrl+train.image;
                    this.company = train;
                    this.company.image = this.photo;
                });
            
        });

    }

    onAvatarChange(event: any): void {
        const inputElement = event.target as HTMLInputElement;
        if (inputElement?.files && inputElement.files.length > 0) {
          const file = inputElement.files[0];
      
          // Create a FileReader to read the selected image and create an Object URL
          const reader = new FileReader();
          reader.onload = (e: any) => {
            this.photo = e.target.result; // Set the Object URL as the image preview
            this.companyDetailsForm.get('image').setValue(file); // Update the form control value with the selected file
          };
          reader.readAsDataURL(file);
        }
    }

    goToCompaniesList() {
        this.router.navigateByUrl('/company');
    }
    upload(event: Event) {
        console.log(event);
    }

    // subscribe(): void {
    //     const paymentRequest = {
    //         currency: 'usd',
    //         amount: this.training.price,
    //         name: this.training.title, 
    //         successUrl: 'http://localhost:4200/user/success',
    //         cancelUrl: 'http://localhost:4200/user/cancel'
    //       };
    //       this.paymentService.initiatePayment(paymentRequest).subscribe(response => {
    //         this.paymentUrl = response.url;
    //         window.location.href = this.paymentUrl;
    //       }, error => {
    //         console.error('Error initiating payment:', error);
    //       });
    //     }
    like(): void {
      

        const companyId = this.company.id;
        var form_data = new FormData();

        for ( var key in this.company ) {
         form_data.append(key, this.company[key]);
        }
        this.company.rating++;
        this.companyService.rateCompany(companyId,this.company).subscribe(() => {

        });
        this.company.rating = Math.round(this.company.rating);

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
        reload() {
            this.reloadComponent(false, 'CompanyDetailComponentUser');
        }
}