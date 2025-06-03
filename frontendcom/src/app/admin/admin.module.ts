import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AdminRoutingModule } from './admin-routing.module';
import { RouterOutlet } from '@angular/router';

import { InputNumber, InputNumberModule } from 'primeng/inputnumber';
@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    AdminRoutingModule,
    RouterOutlet,
    InputNumberModule,
    
  ]
})
export class AdminModule { }
