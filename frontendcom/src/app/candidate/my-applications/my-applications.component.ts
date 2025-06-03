import { Component } from '@angular/core';
import { Subscription } from 'rxjs';
import { ApplicationService } from '../../services/application-service/application.service';
import { NotificationService } from '../../services/notification-service/notification.service';
import { AuthService } from '../../services/auth-service/auth.service';

@Component({
  selector: 'app-my-applications',
  templateUrl: './my-applications.component.html',
  styleUrl: './my-applications.component.css'
})
export class MyApplicationsComponent {
applications:any[] = [];
  applicationSubscription?: Subscription;
  cols:any[] = [];
  visible: boolean = false;
  selectedApplication:any;
  userInfo:any;
  constructor(
    private applicationService: ApplicationService,
    private notification: NotificationService,
    private authService: AuthService
  ){

  }

   ngOnInit(): void{
    this.userInfo = this.authService.getUserDetails();
      this.initializeColumns();
      this.getAllApplications();
    }

    private initializeColumns(){
      this.cols.push(
        { field: 'candidateName', header: 'Candidate Name', sortable: false,  },
        { field: 'jobTitle', header: 'Job', sortable: false,  },
        { field: 'appliedDate', header: 'Applied Date', sortable: false,  },
        { field: 'status', header: 'Status', sortable: false,  },
        { field: 'action', header: 'Action', sortable: false,  },
      )
    }
  
    private getAllApplications(){
      this.applicationSubscription = this.applicationService.getAllCandidateApplication(this.userInfo.candidateId).subscribe(response=>{
        this.applications = response;
      },(error:any)=>{
        this.notification.showError('Server Error');
      })
    }

    public revokeApplication(rowData:any){
      this.applicationSubscription = this.applicationService.revokeApplication(rowData.id).subscribe(response=>{
        this.notification.showSuccess('Application Revoked.');
      },(error:any)=>{
        this.notification.showError('Server Error');
      },()=>{
        this.getAllApplications();
      })
    }
  
}
