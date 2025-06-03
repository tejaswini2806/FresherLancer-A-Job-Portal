import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ApplicationService {

  constructor(
      private http: HttpClient, 
    ) { }
  
    getAllApplications(): Observable<any> {
        return this.http.get<{ token: string }>(`${environment.apiUrl}/v1/applications/search`);
    }

    getAllCandidateApplication(candidateId:number): Observable<any> {
      return this.http.get<{ token: string }>(`${environment.apiUrl}/v1/applications/candidate/${candidateId}/search`);
    }

    rejectApplication(applicationId:number){
      return this.http.get<{ token: string }>(`${environment.apiUrl}/v1/applications/${applicationId}/reject`);
    }

    approveApplication(applicationId:number){
      return this.http.get<{ token: string }>(`${environment.apiUrl}/v1/applications/${applicationId}/approve`);
    }

    shortlistApplication(applicationId:number){
      return this.http.get<{ token: string }>(`${environment.apiUrl}/v1/applications/${applicationId}/shortlist`);
    }

    revokeApplication(applicationId:number){
      return this.http.get<{ token: string }>(`${environment.apiUrl}/v1/applications/${applicationId}/revoke`);
    }
}
