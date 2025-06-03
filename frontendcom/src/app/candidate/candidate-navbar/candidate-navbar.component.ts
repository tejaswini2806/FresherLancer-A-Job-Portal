import { Component } from '@angular/core';
import { MenuItem } from 'primeng/api';
import { AuthService } from '../../services/auth-service/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-candidate-navbar',
  templateUrl: './candidate-navbar.component.html',
  styleUrl: './candidate-navbar.component.css'
})
export class CandidateNavbarComponent {
  items?: MenuItem[];
  userMenu?: MenuItem[];

  constructor(
    private router: Router,
    private authService: AuthService
  ) {}

  ngOnInit() {
      this.userMenu = [
        {
          label: "Profile",
          icon: 'pi pi-user',
          command: () => {
              this.router.navigate(['/profile']);
          }
        },
        {
            label: "Logout",
            icon: 'pi pi-sign-out',
            command: () => {
                this.authService.logout();
            }
        }
      ]
      this.items = [
          {
              label: 'Dashboard',
              icon: 'pi pi-home',
              route: '/dashboard',
          },
          {
              label: 'Job Search',
              icon: 'pi pi-briefcase',
              route: '/jobboard'
          },
          {
            label: 'My Applications',
            icon: 'pi pi-users',
            route: '/applications'
          },
      ];
  }
}
