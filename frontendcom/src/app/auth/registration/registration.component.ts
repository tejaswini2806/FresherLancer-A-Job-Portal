import { Component } from '@angular/core';
import { ReactiveFormsModule, FormsModule, FormGroup, FormControl, Validators } from '@angular/forms';
import { PasswordModule } from 'primeng/password';
import { PRIMENG_MODULES } from '../../primeng-shared';
import { Router } from 'express';
import { AuthService } from '../../services/auth-service/auth.service';
import { NotificationService } from '../../services/notification-service/notification.service';
import { Subscription } from 'rxjs';
import { InputTextModule } from 'primeng/inputtext';
import { FloatLabel } from 'primeng/floatlabel';

@Component({
  selector: 'app-registration',
  standalone: true,
  imports: [PRIMENG_MODULES],
  templateUrl: './registration.component.html',
  styleUrl: './registration.component.css'
})
export class RegistrationComponent {
  registrationForm!: FormGroup;
  signUpSubscription!: Subscription;
   constructor(
      private authService: AuthService, 
      private notification: NotificationService,
    ) {}
    
   ngOnInit(): void {
      
      this.registrationForm = new FormGroup({
        firstName: new FormControl(null, [Validators.required]),
        lastName: new FormControl(null, [Validators.required]),
        email: new FormControl(null, [Validators.required]),
        password: new FormControl(null, [Validators.required])
      });

    }

    onSubmit(){
      const registrationFormData = this.registrationForm.getRawValue();
      this.signUpSubscription = this.authService.register(registrationFormData).subscribe(response => {
        this.notification.showSuccess('Register successfully!');
        this.authService.redirectToCandidateLogin();
      }, (error:any)=>{
        this.notification.showError('Server Error');
      });
    }
  
}
