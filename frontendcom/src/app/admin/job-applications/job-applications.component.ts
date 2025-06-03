import { Component } from '@angular/core';
import { PRIMENG_MODULES } from '../../primeng-shared';
import { ApplicationService } from '../../services/application-service/application.service';
import { Subscription } from 'rxjs';
import { NotificationService } from '../../services/notification-service/notification.service';

@Component({
  selector: 'app-job-applications',
  standalone: true,
  imports: [PRIMENG_MODULES],
  templateUrl: './job-applications.component.html',
  styleUrl: './job-applications.component.css'
})
export class JobApplicationsComponent {

  applications:any[] = [];
  applicationSubscription?: Subscription;
  cols:any[] = [];
  visible: boolean = false;
  selectedApplication:any;

  constructor(
    private applicationService: ApplicationService,
    private notification: NotificationService,
  ){

  }

   ngOnInit(): void{
    
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
      this.applicationSubscription = this.applicationService.getAllApplications().subscribe(response=>{
        this.applications = response;
      },(error:any)=>{
        this.notification.showError('Server Error');
      })
    }

    public onRejectCandidate(rowData:any){
      this.applicationSubscription = this.applicationService.rejectApplication(rowData.id).subscribe(response=>{
        this.notification.showSuccess('Application Rejected for '+rowData.candidateName);
      },(error:any)=>{
        this.notification.showError('Server Error');
      },()=>{
        this.getAllApplications();
      })
    }

    public onApproveCandidate(rowData:any){
      this.applicationSubscription = this.applicationService.approveApplication(rowData.id).subscribe(response=>{
        this.notification.showSuccess('Application Approved for '+rowData.candidateName);
      },(error:any)=>{
        this.notification.showError('Server Error');
      },()=>{
        this.getAllApplications();
      })
    }

    public markAsShortlist(rowData:any){
      this.applicationSubscription = this.applicationService.shortlistApplication(rowData.id).subscribe(response=>{
        this.notification.showSuccess('Candidate Shortlisted.');
      },(error:any)=>{
        this.notification.showError('Server Error');
      },()=>{
        this.getAllApplications();
      })
    }
  
}
