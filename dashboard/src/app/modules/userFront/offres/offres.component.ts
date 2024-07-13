import { Component, ViewEncapsulation } from '@angular/core';
import { OfferService } from 'app/modules/admin/offer/offerService';

@Component({
    selector     : 'offres',
    templateUrl  : './offres.component.html',
    encapsulation: ViewEncapsulation.None
})
export class OffresComponentAdmin
{
    dataSource: any;
    /**
     * Constructor
     */
    constructor(private offerService: OfferService,)
    {
        this.offerService.getOffer().subscribe((data) => {
            this.dataSource = data;
            console.log(this.dataSource);
        }); 
    }
}
