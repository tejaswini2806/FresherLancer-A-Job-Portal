import { Component } from '@angular/core';
import { ConfirmationService, MessageService } from 'primeng/api';
import { AuthService } from '../../services/auth-service/auth.service';
import { JobService } from '../../services/job-service/job.service';
import { NotificationService } from '../../services/notification-service/notification.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-job-board',
  templateUrl: './job-board.component.html',
  styleUrl: './job-board.component.css'
})
export class JobBoardComponent {

  jobBoardSubscription?: Subscription
  jobs:any[] = [];
  selectedJob:any;
  jobDetailsVisible:boolean = false;
  userInfo:any;
  constructor(
    private confirmationService: ConfirmationService, 
    private messageService: MessageService,
    private authService: AuthService,
    private jobService: JobService,
    private notification: NotificationService
  ) {}
  
  ngOnInit() {
    this.userInfo = this.authService.getUserDetails();
    this.getJobDetailList();
  }

  public getSeverity(item:any):string{
    return 'success';
  }

  private getJobDetailList(){
    this.jobBoardSubscription = this.jobService.getJobDetailsByCandidate(this.userInfo.candidateId).subscribe((response:any)=>{
      this.jobs = response;
    },(error:any)=>{
      this.notification.showError('Server Error');
    });
  }

  public showJobDetailsDialog(rowData:any){
    this.selectedJob = rowData;
    this.jobDetailsVisible =  true;
  }

  public applyToJob(){
    this.jobBoardSubscription = this.jobService.applyToJob(this.selectedJob.id,this.userInfo.candidateId).subscribe((response:any)=>{
      this.notification.showSuccess('Job Application Submitted.');
    },(error:any)=>{
      this.notification.showError('Server Error');
    },()=>{
      this.jobDetailsVisible = false;
      this.selectedJob = null;
      this.getJobDetailList();
    });
  }

  public onApplyCLick(event: Event) {
    this.confirmationService.confirm({
        target: event.target as EventTarget,
        message: 'Are you sure that you want to proceed?',
        header: 'Confirmation',
        icon: 'pi pi-exclamation-triangle',
        acceptIcon:"none",
        rejectIcon:"none",
        rejectButtonStyleClass:"p-button-text",
        accept: () => {
          this.applyToJob();
        },
        reject: () => {
          // 
        }
    });
 }

 getFirstLetter(value: string): string {
  return value ? value.charAt(0).toUpperCase() : '';
}
}
