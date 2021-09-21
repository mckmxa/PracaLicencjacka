import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

const API_URL = 'https://localhost:8080/api/fetch'

@Injectable({
  providedIn: 'root'
})
export class DbUpdateService {

  constructor(private http: HttpClient) { }

  updateDB(): Observable<any> {
    return this.http.get(API_URL);
  }
}
