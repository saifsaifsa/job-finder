import { Component, OnInit, ViewChild, ViewEncapsulation } from "@angular/core";
import { FormControl, FormGroup, NgForm, Validators } from "@angular/forms";
import { ActivatedRoute, Router } from "@angular/router";
import { fuseAnimations } from "@fuse/animations";
import { FuseAlertType } from "@fuse/components/alert";
import { TrainingCategories } from "app/core/training/training.enums";
import { DatePipe } from '@angular/common';
import { TrainingService } from "app/core/training/trainingService";
import { Training } from "app/core/training/training.types";
import { PaymentService } from "app/core/payment/paymentService";

@Component({
    selector: 'app-training-detail',
    templateUrl: './trainingDetail.component.html',
    encapsulation: ViewEncapsulation.None,
    animations: fuseAnimations,
    providers: [DatePipe]
})
export class TrainingDetailComponentUser implements OnInit {
    @ViewChild('TrainingDetailsNgForm') trainingDetailsNgForm: NgForm;
    private fileUrl = 'http://localhost:8080/api/files/';
    photo: string | ArrayBuffer | null = null;
    paymentUrl: string = '';

    alert: { type: FuseAlertType; message: string } = {
        type: 'success',
        message: '',
    };
    showAlert: boolean = false;
    trainingDetailsForm: FormGroup;
    isUpdating: boolean = false;
    trainingCategories = Object.values(TrainingCategories);
    training:Training;
    constructor(
        private trainingService: TrainingService,
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
            const trainingId = params['id'];
                this.trainingService.getTraining(trainingId).subscribe((train: any) => {
                    this.photo = this.fileUrl+train.image;
                    this.training = train;
                    this.training.image = this.photo;
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
            this.trainingDetailsForm.get('image').setValue(file); // Update the form control value with the selected file
          };
          reader.readAsDataURL(file);
        }
    }

    goToTrainingsList() {
        this.router.navigateByUrl('/training');
    }
    upload(event: Event) {
        console.log(event);
    }

    subscribe(): void {
        const paymentRequest = {
            currency: 'usd',
            amount: this.training.price,
            name: this.training.title, 
            successUrl: 'http://localhost:4200/success',
            cancelUrl: 'http://localhost:4200/cancel'
          };
          this.paymentService.initiatePayment(paymentRequest).subscribe(response => {
            this.paymentUrl = response.url;
            window.location.href = this.paymentUrl;
          }, error => {
            console.error('Error initiating payment:', error);
          });
        }
    like(): void {
        const trainingId = this.training.id;
        this.trainingService.likeTraining(trainingId).subscribe(() => {
        });
        }
    dislike(): void {
        const trainingId = this.training.id;
        this.trainingService.dislikeTraining(trainingId).subscribe(() => {

        });
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
            this.reloadComponent(false, 'TrainingModuleUser');
        }
}