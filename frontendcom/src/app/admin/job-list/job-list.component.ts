import { Component } from '@angular/core';
import { PRIMENG_MODULES } from '../../primeng-shared';
import { Subscription } from 'rxjs';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { NotificationService } from '../../services/notification-service/notification.service';
import { JobService } from '../../services/job-service/job.service';
import { CandidateService } from '../../services/candidate-service/candidate.service';
import { CommonModule } from '@angular/common';
import { SelectButton } from 'primeng/selectbutton';

@Component({
  selector: 'app-job-list',
  standalone: true,
  imports: [PRIMENG_MODULES, CommonModule],
  templateUrl: './job-list.component.html',
  styleUrl: './job-list.component.css'
})
export class JobListComponent {
  jobs:any[] = [];
  jobSubscription?: Subscription;
  cols:any[] = [];
  visible: boolean = false;
  matchCandidateVisible:boolean = false;
  jobForm!: FormGroup;
  selectedjob:any;
  isUpdate:boolean = false;

  employementTypeOptions= [
    {id: 'FULL_TIME', name: 'Full Time'},
    {id: 'PART_TIME', name: 'Part Time'},
    {id: 'CONTRACT', name: 'Contract'} ,
  ];

  experianceLevelOptions = [
    {id: 'BEGINNER', name: 'Beginner'},
    {id: 'INTERMEDIATE', name: 'Intermediate'},
    {id: 'EXPERIANCED', name: 'Experianced'}
  ]

  candidates:any[] = [];
  matchCandidateColumns:any[] = [];
  constructor(
    private jobService: JobService,
    private notification: NotificationService,
    private candidateService: CandidateService
  ){

  }
  ngOnInit(): void{
    this.jobForm = new FormGroup({
      title: new FormControl(null, [Validators.required]),
      description: new FormControl(null, [Validators.required]),
      location: new FormControl(null,[Validators.required]),
      employmentType: new FormControl(null, [Validators.required]),
      minSalary: new FormControl(null,[Validators.required]),
      maxSalary: new FormControl(null,[Validators.required]),
      skills: new FormControl([], [Validators.required]),
      experienceLevel: new FormControl(null, [Validators.required]),
    });

    this.initializeColumns();
    this.getAllJobs();
  }

  private initializeColumns(){
    this.cols.push(
      { field: 'title', header: 'Title', sortable: false,  },
      { field: 'description', header: 'Description', sortable: false,  },
      { field: 'openings', header: 'Opnenings', sortable: false,  },
      { field: 'status', header: 'Status', sortable: false,  },
      { field: 'action', header: 'Action', sortable: false,  },
    )
    this.matchCandidateColumns.push(
      { field: 'firstName', header: 'First Name', sortable: false,  },
      { field: 'lastName', header: 'Last name', sortable: false,  },
      { field: 'email', header: 'Email', sortable: false,  },
      { field: 'status', header: 'Status', sortable: false,  },
      { field: 'action', header: 'Action', sortable: false,  },
    )
  }

  private getAllJobs(){
    this.jobSubscription = this.jobService.getAllJobs().subscribe((response:any)=>{
      this.jobs = response;
    },(error:any)=>{
      this.notification.showError('Server Error');
    })
  }

  private getMatchingCandidates(){
    this.jobSubscription = this.jobService.getAllMatchingCandidates(this.selectedjob.id).subscribe((response:any)=>{
      this.candidates = response;
    },(error:any)=>{
      this.notification.showError('Server Error');
    })
  }

  ngOnDestroy(): void {
    if (this.jobSubscription) {
      this.jobSubscription.unsubscribe();
    }
  }

  showDialog() {
    this.jobForm.reset();
    this.isUpdate = false;
    this.visible = true;
    
  }

  onSave(){
    const jobFormData = this.jobForm.getRawValue();

    if(this.isUpdate){
      this.jobSubscription = this.jobService.updateJob(jobFormData, this.selectedjob.id).subscribe(response => {
          this.notification.showSuccess('Job Updated successfully!');
        }, (error:any)=>{
          this.notification.showError('Server Error');
        },()=>{
          this.visible = false;
          this.isUpdate = false;
          this.getAllJobs();
        });
    }else{
      this.jobSubscription = this.jobService.createJob(jobFormData).subscribe(response => {
          this.notification.showSuccess('Job Created successfully!');
        }, (error:any)=>{
          this.notification.showError('Server Error');
        },()=>{
          this.visible = false;
          this.getAllJobs();
      });
    }
    
  }

  onClose(){
    this.visible = false;
    this.jobForm.reset();
  }

  onJobEdit(rowData:any){
    this.selectedjob = rowData;
    this.isUpdate = true;
    this.jobForm.patchValue(this.selectedjob);
    this.visible = true;
  }

  onJobDelete(rowData:any){
    this.selectedjob = rowData;
  }

  onMatchCandidate(rowData:any){
    this.selectedjob = rowData;
    this.getMatchingCandidates();
    this.matchCandidateVisible = true;
  }

  onSubmitCandidate(rowData:any){
    this.jobSubscription = this.jobService.submitCandidateToJob(this.selectedjob.id, rowData.id).subscribe(response => {
      this.notification.showSuccess('Candidate Submitted To Job Successfully!');
    }, (error:any)=>{
      this.notification.showError('Candidate Submission Failed.');
    },()=>{
      this.matchCandidateVisible = false;
      this.selectedjob = null;
      this.getAllJobs();
  });
  }
}

