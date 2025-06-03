import { ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import { provideClientHydration } from '@angular/platform-browser';
import { HTTP_INTERCEPTORS, provideHttpClient, withFetch, withInterceptors, withInterceptorsFromDi } from '@angular/common/http';
import { provideAnimations } from '@angular/platform-browser/animations';
import { CardModule } from 'primeng/card';
import { MessageService, PrimeNGConfig } from 'primeng/api';
import { PRIME_NG_PROVIDERS } from './primeng.providers';
import { AuthInterceptor } from './shared/interceptors/auth.interceptor';
import { PRIMENG_MODULES } from './primeng-shared';

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes), 
    provideClientHydration(),
    provideHttpClient(withFetch()),
    provideAnimations(),
    provideHttpClient(withInterceptorsFromDi()),
    PRIME_NG_PROVIDERS,
    PRIMENG_MODULES,
    MessageService,
    { 
      provide: HTTP_INTERCEPTORS, 
      useClass: AuthInterceptor, 
      multi: true }
  ],

};
function providePrimeNG(arg0: { theme: { preset: any; }; }): import("@angular/core").Provider | import("@angular/core").EnvironmentProviders {
  throw new Error('Function not implemented.');
}

