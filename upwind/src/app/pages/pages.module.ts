import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgxTypedJsModule } from 'ngx-typed-js';

import { PagesRoutingModule } from './pages-routing.module';
import { SharedModule } from '../shared/shared.module';
import { IndexComponent } from './index/index.component';
import { IndexTwoComponent } from './index-two/index-two.component';
import { IndexThreeComponent } from './index-three/index-three.component';
import { IndexFourComponent } from './index-four/index-four.component';
import { IndexFiveComponent } from './index-five/index-five.component';
import { IndexSixComponent } from './index-six/index-six.component';
import { IndexSevenComponent } from './index-seven/index-seven.component';
import { IndexEightComponent } from './index-eight/index-eight.component';
import { BlogDetailComponent } from './blog-detail/blog-detail.component';
import { PortofolioDetailComponent } from './portfolio-detail/portfolio-detail.component';

@NgModule({
  declarations: [
    IndexComponent,
    IndexTwoComponent,
    IndexThreeComponent,
    IndexFourComponent,
    IndexFiveComponent,
    IndexSixComponent,
    IndexSevenComponent,
    IndexEightComponent,
    BlogDetailComponent,
    PortofolioDetailComponent,
  ],
  imports: [CommonModule, PagesRoutingModule, SharedModule, NgxTypedJsModule],
})
export class PagesModule { }
