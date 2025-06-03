
import { Injectable } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { environment } from '../../../environments/environment';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CandidateService {

  constructor(
    private http: HttpClient, 
  ) { }

  getAllCandidates(): Observable<any> {
      return this.http.get<{ token: string }>(`${environment.apiUrl}/v1/candidate/search`);
  }

 createCandidate(formData:any): Observable<any> {
    return this.http.post<{ token: string }>(`${environment.apiUrl}/v1/candidate`, formData);
 }
 updateCandidate(formData:any,candidateId:number): Observable<any> {
  return this.http.put<{ token: string }>(`${environment.apiUrl}/v1/candidate/${candidateId}`, formData);
 }
}
