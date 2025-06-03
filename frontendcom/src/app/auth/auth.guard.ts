import { ActivatedRouteSnapshot, CanActivate, CanActivateChild, CanActivateFn, Router, RouterStateSnapshot } from '@angular/router';
import { AuthService } from '../services/auth-service/auth.service';
import { inject, Injectable } from '@angular/core';
import { StorageService } from '../services/storage-service/storage.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuardService implements CanActivate, CanActivateChild {
  constructor(
    private authService: AuthService, 
    private router: Router,
    private storageService: StorageService
  ) {}

  canActivate(
    route: ActivatedRouteSnapshot, 
    state: RouterStateSnapshot
  ): boolean {
    if (!this.authService.isAuthenticated()) {
      this.router.navigate(['/login']);
      return false;
    }

    const userInfo = this.storageService.getFromStorage("USER_INFO");
    var userType = userInfo.userType;
    // Route access restriction based on userType
    if (state.url.startsWith('/admin') && userType !== 'ADMIN') {
      this.redirectUser(userType);
      return false;
    }

    if (state.url.startsWith('/ats') && userType !== 'AGENCY') {
      this.redirectUser(userType);
      return false;
    }

    if (!state.url.startsWith('/admin') && !state.url.startsWith('/ats') && userType !== 'CANDIDATE') {
      this.redirectUser(userType);
      return false;
    }

    return true;
  }

  canActivateChild(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    return this.canActivate(route, state);
  }

  private redirectUser(userType: string | null) {
    switch (userType) {
      case 'ADMIN':
        this.router.navigate(['/admin']);
        break;
      case 'AGENCY':
        this.router.navigate(['/ats']);
        break;
      case 'CANDIDATE':
        this.router.navigate(['/dashboard']);
        break;
      default:
        this.router.navigate(['/login']);
    }
  }

}
