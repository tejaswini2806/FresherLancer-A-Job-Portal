import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class JobService {

  constructor(private http: HttpClient, ) { }

  getAllJobs(){
  return this.http.get<{ token: string }>(`${environment.apiUrl}/v1/jobs/search`);
  }

  getAllMatchingCandidates(jobId:number){
    return this.http.get<{ token: string }>(`${environment.apiUrl}/v1/jobs/matchingcandidates/${jobId}`);
  }

  createJob(jobFormData:any){
    return this.http.post<{ token: string }>(`${environment.apiUrl}/v1/jobs`, jobFormData);
  }

  updateJob(jobFormData:any, jobId:number){
    return this.http.put<{ token: string }>(`${environment.apiUrl}/v1/jobs/${jobId}`, jobFormData);
  }

  submitCandidateToJob(jobId:number, candidateId:number){
    return this.http.get<{ token: string }>(`${environment.apiUrl}/v1/jobs/${jobId}/submitCandidate/${candidateId}`);
  }

  getJobDetailsByCandidate(candidateId:number){
    return this.http.get<{ token: string }>(`${environment.apiUrl}/v1/jobs/jobdetails/${candidateId}`);
  }

  applyToJob(jobId:number, candidateId:number){
    return this.http.get<{ token: string }>(`${environment.apiUrl}/v1/jobs/${jobId}/candidate/${candidateId}/apply`);
  }
}
