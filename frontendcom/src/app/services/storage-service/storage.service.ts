import { Injectable, PLATFORM_ID, Inject } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';

@Injectable({
  providedIn: 'root'
})
export class StorageService {

  constructor(@Inject(PLATFORM_ID) private platformId: Object) { }

  setToStorage(key:string, data: any){
    if(isPlatformBrowser(this.platformId))
      localStorage.setItem(key, JSON.stringify(data));
  }

  getFromStorage(key:string){
    if(isPlatformBrowser(this.platformId)){
      var data = null;
      data = localStorage.getItem(key);
      if(data){
        var json =  JSON.parse(data);
        return json;
      }
    }
    return null;
  }

  removeFromStorage(key:string){
    if(isPlatformBrowser(this.platformId))
      localStorage.removeItem(key);
  }
  
  clearStorage(){
    if(isPlatformBrowser(this.platformId))
      localStorage.clear(); 
  }

  setToken(token:string){
    if(isPlatformBrowser(this.platformId))
      localStorage.setItem('ACCESS_TOKEN', token);
  }

  getToken(){
    if(isPlatformBrowser(this.platformId))
      return localStorage.getItem('ACCESS_TOKEN');
    else return null
  }

  isTokenAvailable():boolean{
    var token = this.getToken();
    if(token){
      return true
    } 
    return false;
  }
}
