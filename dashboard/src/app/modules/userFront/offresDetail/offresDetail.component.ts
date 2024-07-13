import { Component, ElementRef, OnInit, ViewChild, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router'; // Import ActivatedRoute
import { OfferService } from 'app/modules/admin/offer/offerService';

import { jsPDF } from 'jspdf';
import html2canvas from 'html2canvas';

@Component({
    selector     : 'offresDetail',
    templateUrl  : './offresDetail.component.html',
    encapsulation: ViewEncapsulation.None
})
export class OffresDetailComponentAdmin implements OnInit
{
    @ViewChild('content') content: ElementRef;
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

      generatePDF() {
        const doc = new jsPDF();
        const content = this.content.nativeElement;
        const imgData = 'assets/images/welcom.png';

       
        const imgWidth = doc.internal.pageSize.getWidth();
        const imgHeight = doc.internal.pageSize.getHeight();

       
        doc.addImage(imgData, 'PNG', 0, 0, imgWidth, imgHeight, undefined, 'FAST');
        doc.addPage(); // Add a new page for the rest of the content

        // Temporarily adjust styles to ensure all content is rendered
      

        html2canvas(content, {
            scale: 2, // Increase scale to get higher quality canvas
            useCORS: true
        }).then((canvas) => {
            const imgData = canvas.toDataURL('image/jpeg', 0.5); // Reduce quality to 50%
            const imgWidth = 180;
            const pageHeight = 295;
            const imgHeight = (canvas.height * imgWidth) / canvas.width;
            let heightLeft = imgHeight;
            let position = 20;
            let pageNumber = 1;

            // Function to add a new page with a red background
            const addRedBackgroundPage = () => {
                doc.addPage();
                doc.setFillColor(241, 245, 249); // Set fill color to #F1F5F9
                doc.rect(0, 0, doc.internal.pageSize.getWidth(), doc.internal.pageSize.getHeight(), 'F'); // Draw a filled rectangle covering the entire page
            };

            doc.setFillColor(241, 245, 249); // Set fill color to #F1F5F9
            doc.rect(0, 0, doc.internal.pageSize.getWidth(), doc.internal.pageSize.getHeight(), 'F'); // Draw a filled rectangle covering the entire page

            // Add the content image on the first page
            doc.addImage(imgData, 'JPEG', 15, position, imgWidth, imgHeight);
            doc.setLineWidth(0.2);
            doc.rect(6, 6, imgWidth + 14, 295 - 22); // Rectangle around the content
            doc.setFontSize(10);
            doc.setTextColor(0, 0, 0); // Set text color to black
            doc.text(`Page ${pageNumber}`, doc.internal.pageSize.getWidth() / 2, pageHeight - 2, { align: 'center' });

            heightLeft -= pageHeight - position;

            while (heightLeft > 0) {
                position = heightLeft - imgHeight + 15;
                addRedBackgroundPage();
                pageNumber++; // Increment page number
                doc.addImage(imgData, 'JPEG', 15, position, imgWidth, imgHeight);
                heightLeft -= pageHeight;

                doc.setLineWidth(0.2);
                doc.rect(6, 6, imgWidth + 14, 295 - 12);
                doc.text(`Page ${pageNumber}`, doc.internal.pageSize.getWidth() / 2, pageHeight - 2, { align: 'center' }); // Add page number after the image
            }

            doc.save('offre.pdf');

   
        });
    }
}
