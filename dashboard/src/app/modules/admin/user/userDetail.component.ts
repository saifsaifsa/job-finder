import { Component, OnInit, ViewChild, ViewEncapsulation } from '@angular/core';
import { FormControl, FormGroup, NgForm, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { fuseAnimations } from '@fuse/animations';
import { FuseAlertType } from '@fuse/components/alert';
import { UserService } from 'app/core/user/user.service';

@Component({
    selector: 'app-user-detail',
    templateUrl: './userDetail.component.html',
    encapsulation: ViewEncapsulation.None,
    animations: fuseAnimations,
})
export class UserDetailComponent implements OnInit {
    @ViewChild('userDetailNgForm') userDetailNgForm: NgForm;

    roles = {
        "ROLE_USER":0,
        "ROLE_MODERATOR":1,
        "ROLE_ADMIN":2,
    };

    alert: { type: FuseAlertType; message: string } = {
        type: 'success',
        message: '',
    };
    showAlert: boolean = false;
    userDetailsForm: FormGroup;
    isUpdating: boolean = false;
    avatar:any;
    authentifiedUser:any
    
    constructor(
        private userService: UserService,
        private router: Router,
        private route: ActivatedRoute
    ) {}

    ngOnInit(): void {
        this.userService.getLoggedInUser().subscribe((user)=>{
            this.authentifiedUser = user
        })
        this.userDetailsForm = new FormGroup({
            lastName: new FormControl('', Validators.required),
            firstName: new FormControl('', Validators.required),
            username: new FormControl('', Validators.required),
            email: new FormControl('', [
                Validators.required,
                Validators.minLength(8),
            ]),
            password: new FormControl('', [
                Validators.required,
                Validators.minLength(8),
                Validators.pattern(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).{8,}$/),
            ]),
            phone: new FormControl('', [
                Validators.required,
                Validators.pattern(/^\+216(20|21|22|23|24|25|26|27|28|29|50|52|53|54|55|56|58|90|91|92|93|94|95|96|97|98|99)\d{6}$/)
            ]),
            role: new FormControl(this.roles[1], Validators.required),
            photo: new FormControl(null),
        });

        this.route.params.subscribe((params) => {
            const userId = params['id'];

            if (userId) {
                this.isUpdating = true;
                this.userDetailsForm = new FormGroup({
                    lastName: new FormControl('', Validators.required),
                    firstName: new FormControl('', Validators.required),
                    username: new FormControl('', Validators.required),
                    email: new FormControl('', [
                        Validators.required,
                        Validators.minLength(8),
                    ]),
                    phone: new FormControl('', [
                        Validators.required,
                        Validators.pattern(/^\+216(20|21|22|23|24|25|26|27|28|29|50|52|53|54|55|56|58|90|91|92|93|94|95|96|97|98|99)\d{6}$/)
                    ]),
                    role: new FormControl(this.roles[1], Validators.required),
                    photo: new FormControl(null),
                });
                this.userService.getUser(userId).subscribe((user: any) => {
                    this.avatar = user.image
                    this.userDetailsForm.patchValue({
                        lastName: user.lastName,
                        firstName: user.firstName,
                        email: user.email,
                        password: '',
                        phone: user.phone,
                        role: this.roles[user.role],
                        photo:user.image,
                        username:user.username
                    });
                });
            }
        });
    }

    // onAvatarChange(event: Event): void {
    //     const inputElement = event.target as HTMLInputElement;
    //     if (inputElement?.files && inputElement.files.length > 0) {
    //         const file = inputElement.files[0];
    //         this.userDetailsForm.patchValue({
    //             photo: file,
    //         });
    //     }
    // }

    onAvatarChange(event: Event): void {
        const inputElement = event.target as HTMLInputElement;
        if (inputElement?.files && inputElement.files.length > 0) {
          const file = inputElement.files[0];
      
          // Create a FileReader to read the selected image and create an Object URL
          const reader = new FileReader();
          reader.onload = (e: any) => {
            this.avatar = e.target.result; // Set the Object URL as the image preview
            this.userDetailsForm.get('photo').setValue(file); // Update the form control value with the selected file
          };
          reader.readAsDataURL(file);
        }
      }
      
      

    goToUsersList() {
        this.router.navigateByUrl('/users');
    }
    upload(event: Event) {
        console.log(event);
    }
    onSubmit(): void {
        this.showAlert = false;
        this.userDetailsForm.disable();
        // const formData = new FormData();
        // formData.append('lastName', this.userDetailsForm.get('lastName').value);
        // formData.append('firstName', this.userDetailsForm.get('firstName').value);
        // formData.append('email', this.userDetailsForm.get('email').value);
        // if (!this.isUpdating) {
        //     formData.append(
        //         'password',
        //         this.userDetailsForm.get('password').value
        //     );
        // }
        // formData.append('phone', this.userDetailsForm.get('phone').value);
        // formData.append('role', this.userDetailsForm.get('role').value);
        // formData.append('photo', this.userDetailsForm.get('photo').value);
        let user = {
            lastName:this.userDetailsForm.get("lastName").value,
            firstName:this.userDetailsForm.get("firstName").value,
            email:this.userDetailsForm.get("email").value,
            phone:this.userDetailsForm.get("phone").value,
            role:this.userDetailsForm.get("role").value,
            username:this.userDetailsForm.get("username").value,
        }
        if (!this.isUpdating) {
            user = {...{password:this.userDetailsForm.get("password").value},...user}
        }
        
        if (this.isUpdating) {
            const userId = this.route.snapshot.params['id'];
            this.userService.updateUser(userId, user).subscribe(
                (res:any) => {
                    this.userService.getLoggedInUser().subscribe((user)=>{
                    if(user.id == userId){
                        this.userService.updateLoggedInUser(res)
                    }    
                    })
                    // if(this.userService.getLoggedInUser()._id == userId){
                    //     this.userService.setLoggedInUser(res)
                    // }
                    this.goToUsersList();
                },
                (error) => {
                    if (error.status === 409) {
                        this.alert = {
                            type: 'error',
                            message: 'Adresse e-mail ou phone est déjà utilisée',
                        };
                    } else if (error.status === 400) {
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
                    this.userDetailsForm.enable();
                    this.showAlert = true;
                }
            );
        } else {
            // Perform add operation
            this.userService.addUser(user).subscribe(
                () => {
                    this.goToUsersList();
                },
                (error) => {
                    if (error.status === 409) {
                        this.alert = {
                            type: 'error',
                            message: 'E-mail or phone number already used',
                        };
                    } else if (error.status === 400) {
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
                    this.userDetailsForm.enable();
                    this.showAlert = true;
                }
            );
        }
    }
}
