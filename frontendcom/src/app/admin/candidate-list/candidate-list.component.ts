import { Component, OnInit } from '@angular/core';
import { PRIMENG_MODULES } from '../../primeng-shared';
import { CandidateService } from '../../services/candidate-service/candidate.service';
import { Subscription } from 'rxjs';
import { NotificationService } from '../../services/notification-service/notification.service';
import { PRIME_NG_PROVIDERS } from '../../primeng.providers';
import { FormControl, FormGroup, Validators } from '@angular/forms';


@Component({
  selector: 'app-candidate-list',
  standalone: true,
  imports: [PRIMENG_MODULES],
  templateUrl: './candidate-list.component.html',
  styleUrl: './candidate-list.component.css',
  providers: [CandidateService, ]
})
export class CandidateListComponent implements OnInit{

  candidates:any[] = [];
  candidateSubscription?: Subscription;
  cols:any[] = [];
  visible: boolean = false;
  candidateForm!: FormGroup;
  selectedCandidate:any;
  isUpdate:boolean = false;

  constructor(
    private candidateService: CandidateService,
    private notification: NotificationService,
  ){

  }
  ngOnInit(): void{
    this.candidateForm = new FormGroup({
      firstName: new FormControl(null, [Validators.required]),
      lastName: new FormControl(null, [Validators.required]),
      email: new FormControl(null, [Validators.required]),
    });

    this.initializeColumns();
      this.getAllCandidates();
  }

  private initializeColumns(){
    this.cols.push(
      { field: 'firstName', header: 'First Name', sortable: false,  },
      { field: 'lastName', header: 'Last Name', sortable: false,  },
      { field: 'email', header: 'Email', sortable: false,  },
      { field: 'status', header: 'Status', sortable: false,  },
      { field: 'action', header: 'Action', sortable: false,  },
    )
  }

  private getAllCandidates(){
    this.candidateSubscription = this.candidateService.getAllCandidates().subscribe(response=>{
      this.candidates = response;
    },(error:any)=>{
      this.notification.showError('Server Error');
    })
  }

  ngOnDestroy(): void {
    if (this.candidateSubscription) {
      this.candidateSubscription.unsubscribe();
    }
  }

  showDialog() {
    this.candidateForm.reset();
    this.isUpdate = false;
    this.visible = true;
    
  }

  onSave(){
    const candidateFormData = this.candidateForm.getRawValue();

    if(this.isUpdate){
      this.candidateSubscription = this.candidateService.updateCandidate(candidateFormData, this.selectedCandidate.id).subscribe(response => {
          this.notification.showSuccess('Candidate Updated successfully!');
        }, (error:any)=>{
          this.notification.showError('Server Error');
        },()=>{
          this.visible = false;
          this.isUpdate = false;
          this.getAllCandidates();
        });
    }else{
      this.candidateSubscription = this.candidateService.createCandidate(candidateFormData).subscribe(response => {
          this.notification.showSuccess('Candidate Created successfully!');
        }, (error:any)=>{
          this.notification.showError('Server Error');
        },()=>{
          this.visible = false;
          this.getAllCandidates();
      });
    }
    
  }

  onClose(){
    this.visible = false;
    this.candidateForm.reset();
  }

  onCandidateEdit(rowData:any){
    this.selectedCandidate = rowData;
    this.isUpdate = true;
    this.candidateForm.patchValue(this.selectedCandidate);
    this.visible = true;
  }

  onCandidateDelete(rowData:any){
    this.selectedCandidate = rowData;
  }
}
