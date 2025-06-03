import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, tap } from 'rxjs';
import { environment } from '../../../environments/environment';
import { StorageService } from '../storage-service/storage.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private token: string | null = null;

  constructor(
    private http: HttpClient, 
    private router: Router,
    private storageService: StorageService,
  ) {}

  login(username: string, password: string): Observable<any> {
    return this.http.post<{ token: string }>(`${environment.apiUrl}/v1/auth/login`, { username, password }).pipe(
      tap((response: any) => {
        this.setToken(response.authToken);
      })
    );
  }

  register(formData:any): Observable<any> {
    return this.http.post<{ token: string }>(`${environment.apiUrl}/v1/unsecured/auth/registration`, formData);
  }

  private setToken(token: string): void {
    this.token = token;
    this.storageService.setToken(token);
  }

  getToken(): string | null {
    return this.token || this.storageService.getToken();
  }

  logout(): void {
    this.token = null;
    this.storageService.clearStorage();
    this.router.navigate(['/login']);
  }

  isAuthenticated(): boolean {
    return this.getToken() !== null;
  }
  
  redirectLogin(userType:string){
    switch (userType) {
      case 'ADMIN':
        this.router.navigate(['/admin']);
        break;
      case 'AGENCY':
        this.router.navigate(['/ats']);
        break;
      case 'CANDIDATE':
        this.router.navigate(['/']);
        break;
      default:
        this.router.navigate(['/login']);
    }
  }

  redirectToCandidateLogin(){
    this.router.navigate(['/']);
  }

  getUserDetails(){
    return this.storageService.getFromStorage('USER_INFO')
  }

}
