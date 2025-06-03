import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient){

  }

  private currentUserType = new BehaviorSubject<string | null>(null);
  currentUserType$ = this.currentUserType.asObservable();

  login(userType: string) {
    this.currentUserType.next(userType);
  }

  logout() {
    this.currentUserType.next(null);
  }

  getCurrentUserType() {
    return this.currentUserType.value;
  }

  getUserDetails(userId:number){
    return this.http.get<{ token: string }>(`${environment.apiUrl}/v1/users/${userId}`); 
  }
}
