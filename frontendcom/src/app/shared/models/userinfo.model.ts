export class AuthResponse {
    token?: string;
    userType?: 'ADMIN' | 'AGENCY' | 'CANDIDATE' ;
    username?: string;
    [key: string]: any; // optional catch-all if more fields are present
  }