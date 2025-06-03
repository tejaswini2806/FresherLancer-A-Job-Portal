import { Routes } from '@angular/router';
import { LoginComponent } from './auth/login/login.component';
import { RegistrationComponent } from './auth/registration/registration.component';
import { AuthGuardService } from './auth/auth.guard';

export const routes: Routes = [
    { path: 'login', component: LoginComponent },
    { path: 'registration', component: RegistrationComponent },
    { path: 'admin', loadChildren: () => 
        import('./admin/admin.module').then(m => m.AdminModule), canActivate: [AuthGuardService] 
    },
    { path: 'ats', loadChildren: () => 
        import('./agency/agency.module').then(m => m.AgencyModule), canActivate: [AuthGuardService] 
    },
    { path: '', loadChildren: () => 
        import('./candidate/candidate.module').then(m => m.CandidateModule), canActivate: [AuthGuardService] 
    },
    { path: '**', redirectTo: 'login' }
];
