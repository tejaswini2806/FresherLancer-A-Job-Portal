import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth-service/auth.service';
import { PRIMENG_MODULES } from '../../primeng-shared';
import { PasswordModule } from 'primeng/password';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';
import { NotificationService } from '../../services/notification-service/notification.service';
import { error } from 'console';
import { StorageService } from '../../services/storage-service/storage.service';
import { userInfo, UserInfo } from 'os';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [PRIMENG_MODULES, PasswordModule, ReactiveFormsModule, FormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent implements OnInit{
  loginForm!: FormGroup;
  signInSubscription!: Subscription;
  constructor(
    private authService: AuthService, 
    private router: Router,
    private notification: NotificationService,
    private storageService: StorageService,
  ) {}

  ngOnInit(): void {
    if (this.authService.isAuthenticated()) {
      const userInfo = this.storageService.getFromStorage("USER_INFO");
      switch (userInfo.userType) {
        case 'ADMIN':
          this.router.navigate(['/admin']);
          break;
        case 'AGENCY':
          this.router.navigate(['/ats']);
          break;
        case 'CANDIDATE':
          this.router.navigate(['/dashboard']);
          break;
      }
    }
    this.loginForm = new FormGroup({
      username: new FormControl(null, [Validators.required]),
      password: new FormControl(null, [Validators.required]),
    })
  }

  onSubmit(){

    const loginFromData = this.loginForm.getRawValue();
    this.authService.login(loginFromData.username, loginFromData.password).subscribe(response => {
      this.notification.showSuccess('Login successfully!');
      var userInfo = response.userInfo;
      this.storageService.setToStorage("USER_INFO", userInfo);
      this.authService.redirectLogin(userInfo.userType);
    }, (error:any)=>{
      this.notification.showError('Invalid credentials');
    });
  }

}
