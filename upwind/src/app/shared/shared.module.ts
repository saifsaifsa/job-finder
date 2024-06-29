import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

// import { CountToModule } from 'angular-count-to';
import { LightboxModule } from 'ngx-lightbox';
import { ModalVideoComponent } from './modal-video/modal-video.component';

import { FooterComponent } from './footer/footer.component';
import { SwitcherComponent } from './switcher/switcher.component';
import { AboutComponent } from './about/about.component';
import { ServicesComponent } from './services/services.component';
import { PortfolioComponent } from './portfolio/portfolio.component';
import { ReviewComponent } from './review/review.component';
import { PricingComponent } from './pricing/pricing.component';
import { BlogComponent } from './blog/blog.component';
import { ContactusComponent } from './contactus/contactus.component';
import { NavbarComponent } from './navbar/navbar.component';
import { TeamComponent } from './team/team.component';
import { VideopopupDirective } from './videopopup.directive';
import { SafePipe } from './safe.pipe';

@NgModule({
  declarations: [
    FooterComponent,
    SwitcherComponent,
    AboutComponent,
    ServicesComponent,
    PortfolioComponent,
    ReviewComponent,
    PricingComponent,
    BlogComponent,
    ContactusComponent,
    NavbarComponent,
    TeamComponent,
    ModalVideoComponent,
    VideopopupDirective,
    SafePipe
  ],
  imports: [CommonModule, LightboxModule],
  exports: [
    FooterComponent,
    SwitcherComponent,
    AboutComponent,
    ServicesComponent,
    PortfolioComponent,
    ReviewComponent,
    PricingComponent,
    BlogComponent,
    ContactusComponent,
    NavbarComponent,
    TeamComponent,
    VideopopupDirective,
    ModalVideoComponent,
    SafePipe
  ],
})
export class SharedModule { }
