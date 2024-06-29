import { Component, OnInit, ViewChild, ViewEncapsulation } from "@angular/core";
import { FormControl, FormGroup, NgForm, Validators } from "@angular/forms";
import { ActivatedRoute, Router } from "@angular/router";
import { fuseAnimations } from "@fuse/animations";
import { FuseAlertType } from "@fuse/components/alert";
import { TrainingCategories } from "app/core/training/training.enums";
import { TrainingService } from "app/core/training/trainingService";

@Component({
    selector: 'app-training-detail',
    templateUrl: './trainingDetail.component.html',
    encapsulation: ViewEncapsulation.None,
    animations: fuseAnimations,
})
export class TrainingDetailComponent implements OnInit {
    @ViewChild('TrainingDetailsNgForm') trainingDetailsNgForm: NgForm;

    alert: { type: FuseAlertType; message: string } = {
        type: 'success',
        message: '',
    };
    showAlert: boolean = false;
    trainingDetailsForm: FormGroup;
    isUpdating: boolean = false;
    trainingCategories = Object.values(TrainingCategories);

    constructor(
        private trainingService: TrainingService,
        private router: Router,
        private route: ActivatedRoute
    ) { }

    ngOnInit(): void {
        this.trainingDetailsForm = new FormGroup({
            title: new FormControl('', Validators.required),
            description: new FormControl('', Validators.required),
            trainingCategories: new FormControl('', Validators.required), 
            price: new FormControl('', [Validators.required,Validators.min(1)]),
            rating: new FormControl('', [Validators.required,Validators.min(1)]),
            likes: new FormControl('', Validators.required),
            dislikes: new FormControl('', Validators.required),
            dateDebut: new FormControl('', Validators.required),
            dateFin: new FormControl('', Validators.required),
            // image: new FormControl('', Validators.required),
        });

        this.route.params.subscribe((params) => {
            const trainingId = params['id'];

            if (trainingId) {
                this.isUpdating = true;
                this.trainingDetailsForm = new FormGroup({
                    title: new FormControl('', Validators.required),
                    description: new FormControl('', Validators.required),
                    trainingCategories: new FormControl('', Validators.required), 
                    price: new FormControl('', [Validators.required,Validators.min(1)]),
                    rating: new FormControl('', [Validators.required,Validators.min(1)]),
                    likes: new FormControl('', Validators.required),
                    dislikes: new FormControl('', Validators.required),
                    dateDebut: new FormControl('', Validators.required),
                    dateFin: new FormControl('', Validators.required),
                    // image: new FormControl('', Validators.required),
                });
                this.trainingService.getTraining(trainingId).subscribe((training: any) => {
                    this.trainingDetailsForm.patchValue({
                        title: training.title,
                        description: training.description,
                        trainingCategories: training.trainingCategories,
                        price: training.price,
                        rating: training.rating,
                        likes: training.likes,
                        dislikes: training.likes,
                        dateDebut: training.dateDebut,
                        dateFin: training.dateDebut,
                        // image: training.image
                    });
                });
            }
        });
    }

    onAvatarChange(event: Event): void {
        const inputElement = event.target as HTMLInputElement;
        if (inputElement?.files && inputElement.files.length > 0) {
            const file = inputElement.files[0];
            this.trainingDetailsForm.patchValue({
                image: file,
            });
        }
    }

    goToTrainingsList() {
        this.router.navigateByUrl('/training');
    }
    upload(event: Event) {
        console.log(event);
    }
    onSubmit(): void {
        this.showAlert = false;
        this.trainingDetailsForm.disable();
        const formData = new FormData();
        formData.append('title', this.trainingDetailsForm.get('title').value);
        formData.append('description', this.trainingDetailsForm.get('description').value);
        formData.append('trainingCategories', this.trainingDetailsForm.get('trainingCategories').value);
        formData.append('rating', this.trainingDetailsForm.get('rating').value);
        formData.append('price', this.trainingDetailsForm.get('price').value);
        formData.append('likes', this.trainingDetailsForm.get('likes').value);
        formData.append('dislikes', this.trainingDetailsForm.get('dislikes').value);
        formData.append('dateDebut', this.trainingDetailsForm.get('dateDebut').value);
        formData.append('dateFin', this.trainingDetailsForm.get('dateFin').value);
        // formData.append('image', this.trainingDetailsForm.get('image').value);

        if (this.isUpdating) {
            const trainingId = this.route.snapshot.params['id'];
            var object = {};
            formData.forEach(function(value, key){
                object[key] = value;
            });
            object['id'] = trainingId;
            var json = JSON.stringify(object);
            console.log(json)
            this.trainingService
                .updateTraining(trainingId, json)
                .subscribe(
                    () => {
                        this.goToTrainingsList();
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
                        this.trainingDetailsForm.enable();
                        this.showAlert = true;
                    }
                );
        } else {
            var object = {};
            formData.forEach(function(value, key){
                object[key] = value;
            });
            var json = JSON.stringify(object);
            // Perform add operation
            this.trainingService.createTraining(json).subscribe(
                () => {
                    this.goToTrainingsList();
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
                    this.trainingDetailsForm.enable();
                    this.showAlert = true;
                }
            );
        }
    }
}