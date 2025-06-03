// src/app/shared/primeng.providers.ts

import { importProvidersFrom } from '@angular/core';
import { CardModule } from 'primeng/card';
import { InputTextModule } from 'primeng/inputtext';
import { PasswordModule } from 'primeng/password';
import { ButtonModule } from 'primeng/button';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FloatLabel } from 'primeng/floatlabel';
import { TableModule } from 'primeng/table';
import { Dialog } from 'primeng/dialog';

export const PRIME_NG_PROVIDERS = importProvidersFrom(
  BrowserAnimationsModule,
  CardModule,
  InputTextModule,
  PasswordModule,
  ButtonModule,
  FloatLabel,
  TableModule,
  Dialog
);
