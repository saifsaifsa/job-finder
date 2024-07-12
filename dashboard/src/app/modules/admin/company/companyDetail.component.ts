import { Component, OnInit, ViewChild, ViewEncapsulation } from "@angular/core";
import { FormControl, FormGroup, NgForm, Validators } from "@angular/forms";
import { ActivatedRoute, Router } from "@angular/router";
import { fuseAnimations } from "@fuse/animations";
import { FuseAlertType } from "@fuse/components/alert";
import { DatePipe } from '@angular/common';
import { CompanyService } from "app/core/company/companyService";

@Component({
    selector: 'app-company-detail',
    templateUrl: './companyDetail.component.html',
    encapsulation: ViewEncapsulation.None,
    animations: fuseAnimations,
    providers: [DatePipe]
})
export class CompanyDetailComponent implements OnInit {
    @ViewChild('CompanyDetailsNgForm') companyDetailsNgForm: NgForm;
    private fileUrl = 'http://localhost:8080/api/files/';
    photo: string | ArrayBuffer | null = null;

    alert: { type: FuseAlertType; message: string } = {
        type: 'success',
        message: '',
    };
    showAlert: boolean = false;
    companyDetailsForm: FormGroup;
    isUpdating: boolean = false;
    // trainingCategories = Object.values(TrainingCategories);

    constructor(
        private companyService: CompanyService,
        private router: Router,
        private route: ActivatedRoute,
        private datePipe: DatePipe
    ) { }
    private formatDate(date: Date): string {
        console.log("date:",date);
        console.log("this.datePipe.transform(date, 'yyyy/MM/dd')",this.datePipe.transform(date, 'yyyy/MM/dd'));
        
        return this.datePipe.transform(date, 'yyyy/MM/dd') || '';
      }
    ngOnInit(): void {
        this.companyDetailsForm = new FormGroup({
            industry: new FormControl('', Validators.required),
            description: new FormControl('', Validators.required),
            location: new FormControl('', Validators.required), 
            name: new FormControl('', Validators.required), 
            rating: new FormControl('', [Validators.required,Validators.min(1)]),
            image: new FormControl(null),
        });

        this.route.params.subscribe((params) => {
            const companyId = params['id'];

            if (companyId) {
                this.isUpdating = true;
                this.companyDetailsForm = new FormGroup({
                    industry: new FormControl('', Validators.required),
                    description: new FormControl('', Validators.required),
                    location: new FormControl('', Validators.required), 
                    name: new FormControl('', Validators.required), 
                    rating: new FormControl('', [Validators.required,Validators.min(1)]),
                    image: new FormControl(null),
                });
                this.companyService.getCompany(companyId).subscribe((company: any) => {
                    this.photo = this.fileUrl+company.image 
                    this.companyDetailsForm.patchValue({
                        industry: company.industry,
                        description: company.description,
                        location: company.location,
                        name: company.name,
                        rating: company.rating,
                        image: company.image,
                     
                    });
                });
            }
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
    onSubmit(): void {
        this.showAlert = false;
        this.companyDetailsForm.disable();
        const formData = new FormData();
        formData.append('industry', this.companyDetailsForm.get('industry').value);
        formData.append('description', this.companyDetailsForm.get('description').value);
        formData.append('location', this.companyDetailsForm.get('location').value);
        formData.append('rating', this.companyDetailsForm.get('rating').value);
        formData.append('name', this.companyDetailsForm.get('name').value);
        formData.append('image', this.companyDetailsForm.get('image').value);
     
        // formData.append('image', this.trainingDetailsForm.get('image').value);
        if(this.companyDetailsForm.get('image').value){formData.append('image', this.companyDetailsForm.get('image').value);}

        if (this.isUpdating) {
            const companyId = this.route.snapshot.params['id'];
            formData.append('id', companyId);
            this.companyService
                .updateCompany(companyId, formData)
                .subscribe(
                    () => {
                        this.goToCompaniesList();
                    },
                    (error) => {
                        if (error.status === 400) {
                            console.log(error);
                            this.alert = {
                                type: 'error',
                                message: 'error occured',
                            };
                        } else {
                            this.alert = {
                                type: 'error',
                                message:
                                    'Une erreur est survenue veuillez réessayer ultérieurement',
                            };
                        }
                        this.companyDetailsForm.enable();
                        this.showAlert = true;
                    }
                );
        } else {
            this.companyService.createCompany(formData).subscribe(
                () => {
                    this.goToCompaniesList();
                },
                (error) => {
                    if (error.status === 400) {
                        console.log(error);
                        this.alert = {
                            type: 'error',
                            message: 'error occured',
                        };
                    } else {
                        this.alert = {
                            type: 'error',
                            message:
                                'Une erreur est survenue veuillez réessayer ultérieurement',
                        };
                    }
                    this.companyDetailsForm.enable();
                    this.showAlert = true;
                }
            );
        }
    }
}