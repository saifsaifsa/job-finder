import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router'; // Import ActivatedRoute
import { OfferService } from 'app/modules/admin/offer/offerService';

@Component({
    selector     : 'offresDetail',
    templateUrl  : './offresDetail.component.html',
    encapsulation: ViewEncapsulation.None
})
export class OffresDetailComponentAdmin implements OnInit
{
    dataSource: any;
    userId: string | null = null; 
    offerId: string | null = null; 

    /**
     * Constructor
     */
    constructor(
        private offerService: OfferService,
        private route: ActivatedRoute ,
        private router: Router
    ) {}

    addUserToOffer(): void {
        this.offerService.addUserToOffer(this.offerId, this.userId).subscribe(() => {
            // Handle success
            this.router.navigate(['/offresUser']); 
        }, (error) => {
            console.error(error); 
        });
    }

    ngOnInit(): void {
        // Get the user from local storage
        const user = JSON.parse(localStorage.getItem('user'));

        // Get the user ID
        this.userId= user.id;
        // Get the 'id' from the URL
        this.route.paramMap.subscribe(params => {
            this.offerId = params.get('id'); // Get the 'id' parameter
            console.log(this.offerId);

            if (this.offerId) {
                // Use the offer ID to fetch the offer details
                this.offerService.getOfferById(this.offerId).subscribe((data) => {
                    this.dataSource = data;
                    console.log(this.dataSource);
                });
            }
        });
    }
}
