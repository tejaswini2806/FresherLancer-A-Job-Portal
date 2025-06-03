import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CandidateRoutingModule } from './candidate-routing.module';
import { SharedModule } from '../shared/shared.module';
import { RouterOutlet } from '@angular/router';
import { PRIMENG_MODULES } from '../primeng-shared';
import { CandidateLayoutComponent } from './candidate-layout/candidate-layout.component';
import { CandidateNavbarComponent } from './candidate-navbar/candidate-navbar.component';
import { JobBoardComponent } from './job-board/job-board.component';
import { MyApplicationsComponent } from './my-applications/my-applications.component';
import { ProfileComponent } from './profile/profile.component';
import { ConfirmationService, MessageService } from 'primeng/api';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { ChipsModule } from 'primeng/chips';
import { ChipModule } from 'primeng/chip';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { StepperModule } from 'primeng/stepper';
@NgModule({
  declarations: [
    CandidateLayoutComponent, 
    CandidateNavbarComponent,
    JobBoardComponent,
    MyApplicationsComponent,
    ProfileComponent 
  ],
  imports: [
    RouterOutlet,
    SharedModule,
    FormsModule,
    ReactiveFormsModule,
    CommonModule,
    CandidateRoutingModule,
    PRIMENG_MODULES,
    ChipsModule,
    ChipModule,
    ConfirmDialogModule,
    StepperModule
  ],
  providers:[ConfirmationService, MessageService]
})
export class CandidateModule { }
