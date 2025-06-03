import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CandidateDashboardComponent } from './candidate-dashboard/candidate-dashboard.component';
import { AuthGuardService } from '../auth/auth.guard';
import { CandidateLayoutComponent } from './candidate-layout/candidate-layout.component';
import { ProfileComponent } from './profile/profile.component';
import { MyApplicationsComponent } from './my-applications/my-applications.component';
import { JobBoardComponent } from './job-board/job-board.component';

const routes: Routes = [
  {
      path: '',
      component: CandidateLayoutComponent,
      canActivateChild: [AuthGuardService],
      children: [
        { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
        { path: 'dashboard', component: CandidateDashboardComponent },
        { path: 'jobboard', component: JobBoardComponent },
        { path: 'applications', component: MyApplicationsComponent },
        { path: 'profile', component: ProfileComponent },
      ],
    },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CandidateRoutingModule { }
