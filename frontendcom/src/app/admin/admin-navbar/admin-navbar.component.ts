import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { MenuItem, SharedModule } from 'primeng/api';
import { MenubarModule } from 'primeng/menubar';
import { PRIMENG_MODULES } from '../../primeng-shared';
import { AuthService } from '../../services/auth-service/auth.service';
import { AvatarModule } from 'primeng/avatar';
import { TieredMenuModule } from 'primeng/tieredmenu';
import { DividerModule } from 'primeng/divider';
import { BadgeModule } from 'primeng/badge';

@Component({
  selector: 'app-admin-navbar',
  standalone: true,
  imports: [SharedModule, AvatarModule, MenubarModule,TieredMenuModule, DividerModule, BadgeModule, CommonModule],
  templateUrl: './admin-navbar.component.html',
  styleUrl: './admin-navbar.component.css'
})
export class AdminNavbarComponent {
  items?: MenuItem[];
  userMenu?: MenuItem[];

  constructor(
    private router: Router,
    private authService: AuthService
  ) {}

  ngOnInit() {
      this.userMenu = [
        {
            label: "Logout",
            icon: 'pi pi-sign-out',
            command: () => {
                this.authService.logout();
            }
        }]
      this.items = [
          {
              label: 'Dashboard',
              icon: 'pi pi-home',
              route: '/admin/dashboard',
          },
          {
              label: 'Jobs',
              icon: 'pi pi-briefcase',
              route: '/admin/jobs'
          },
          {
            label: 'Candidate',
            icon: 'pi pi-users',
            route: '/admin/candidates'
          },
          {
            label: 'Applications',
            icon: 'pi pi-clipboard',
            route: '/admin/applications'
          },
          {
            label: 'Reports',
            icon: 'pi pi-file',
            route: '/reports'
          },
      ];
  }
}
