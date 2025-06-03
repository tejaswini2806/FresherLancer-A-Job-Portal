import { Component } from '@angular/core';
import { UserService } from '../../services/user-service/user.service';
import { Subscription } from 'rxjs';
import { AuthService } from '../../services/auth-service/auth.service';
import { FormArray, FormControl, FormGroup, FormGroupName, Validators } from '@angular/forms';
import { NotificationService } from '../../services/notification-service/notification.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css'
})
export class ProfileComponent {

  profileSubscription?:Subscription;
  userDetails:any;
  userInfo:any;

  candidateForm!:FormGroup;
  educationMenu:any[] = [
    {
      label: 'Delete',
      icon: 'pi pi-times'
    }
  ];

  degreeOptions:any[] = [
    { "id": 1, "name": "10th" },
    { "id": 2, "name": "12th" },
    { "id": 3, "name": "Diploma" },
    { "id": 4, "name": "Bachelor's Degree" },
    { "id": 5, "name": "Master's Degree" },
    { "id": 6, "name": "PhD" },
    { "id": 7, "name": "Certification Course" },
    { "id": 8, "name": "Postgraduate Diploma" },
    { "id": 9, "name": "Associate Degree" },
    { "id": 10, "name": "Other" }
  ];
  constructor(
    private userService: UserService,
    private authService: AuthService,
    private notification: NotificationService
  ){}

  ngOnInit(): void{
    this.initialiseForm();
    this.userInfo = this.authService.getUserDetails();
    this.getUserDetails();
  }

  initialiseForm(){
    this.candidateForm = new FormGroup({
      personalDetails: new FormGroup({
        firstName: new FormControl(null, [Validators.required]),
        lastName: new FormControl(null, [Validators.required]),
        email: new FormControl(null, [Validators.required, Validators.email]),
        skills: new FormControl([],[Validators.required]),
      }),
      educationDetails: new FormArray([
        this.createEducationGroup('EDUCATION')
      ]),
      exparianceDetails: new FormArray([
        // this.createEducationGroup('EXPERIANCE')
      ])
    });
  }

  createEducationGroup(val:string): FormGroup {
    if(val === 'EDUCATION'){
      return new FormGroup({
        degree: new FormControl(null, Validators.required),
        institution: new FormControl(null, Validators.required),
        startYear: new FormControl(null, Validators.required),
        endYear: new FormControl(null, Validators.required)
      });
    }else if(val === 'EXPERIANCE'){
      return new FormGroup({
        companyName: new FormControl(null, Validators.required),
        position: new FormControl(null, Validators.required),
        startYear: new FormControl(null, Validators.required),
        endYear: new FormControl(null, Validators.required),
        description: new FormControl(null),
      });
    }else{
      return new FormGroup({});
    }
  }

  get educationDetails(): FormArray {
    return this.candidateForm.get('educationDetails') as FormArray;
  }
  
  addEducation() {
    if (this.educationDetails.length < 3) {
      this.educationDetails.push(this.createEducationGroup('EDUCATION'));
    }
  }
  
  removeEducation(index: number) {
    if (this.educationDetails.length > 1) {
      this.educationDetails.removeAt(index);
    }
  }

  get exparianceDetails(): FormArray {
    return this.candidateForm.get('exparianceDetails') as FormArray;
  }

  addExperiance() {
   this.exparianceDetails.push(this.createEducationGroup('EXPERIANCE')); 
  }
  
  removeExperiance(index: number) {
    this.exparianceDetails.removeAt(index);
  }

  getUserDetails(){
    this.profileSubscription = this.userService.getUserDetails(this.userInfo.candidateId).subscribe((response:any)=>{
    this.candidateForm.patchValue(response);
    
    const educationData = response.educationDetails;
    const eduArray = this.candidateForm.get('educationDetails') as FormArray;
      educationData.forEach((edu:any) => {
        eduArray.push(
          new FormGroup({
            degree: new FormControl(edu.degree, Validators.required),
            institution: new FormControl(edu.institution, Validators.required),
            startYear: new FormControl(new Date(edu.startYear), Validators.required),
            endYear: new FormControl(new Date(edu.endYear), Validators.required),
          })
        );
      });

      const experianceData = response.exparianceDetails;
      const expArray = this.candidateForm.get('exparianceDetails') as FormArray;
      experianceData.forEach((exp:any) => {
        expArray.push(
          new FormGroup({
            companyName: new FormControl(exp.companyName, Validators.required),
            position: new FormControl(exp.position, Validators.required),
            startYear: new FormControl(new Date(exp.startYear), Validators.required),
            endYear: new FormControl(new Date(exp.endYear), Validators.required),
            description: new FormControl(exp.description),
          })
        );
      });

    },(err)=>{
      this.notification.showError('Server Error');
    },()=>{

    });
  }

  onSave(){
    let formData= this.candidateForm.getRawValue();
    console.log(formData);
  }
}
