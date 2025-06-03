import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AgencyDashboardComponent } from './agency-dashboard/agency-dashboard.component';

const routes: Routes = [
  {
      path: '',
      children: [
        { path: 'dashboard', component: AgencyDashboardComponent },
        // Additional child routes can be added here
      ],
    },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AgencyRoutingModule { }
