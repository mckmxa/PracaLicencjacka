import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

const API_WISHLIST_URL = 'https://localhost:8080/api/wishlist';
const API_HISTORY_URL = 'https://localhost:8080/api/history';
const API_ALERT_URL = 'https://localhost:8080/api/alerts';

@Injectable({
  providedIn: 'root'
})
export class WishlistService {
  
  constructor(private http: HttpClient) { }

  getAll(): Observable<any> {
    return this.http.get<any>(API_WISHLIST_URL);
  }

  deleteAll(): Observable<any> {
    return this.http.delete(API_WISHLIST_URL);
  }

  delete(id: any): Observable<any> {
    return this.http.delete(`${API_WISHLIST_URL}/${id}`);
  }

  getHistory(id : any): Observable<any> {
    return this.http.get<any>(`${API_HISTORY_URL}/${id}`)
  }

  getStatus(): Observable<any> {
    return this.http.get<any>(API_WISHLIST_URL + "/status")
  }

  getAlertsInfo(): Observable<any> {
    return this.http.get<any>(API_ALERT_URL)
  }

  startTracking(id: any,valueAlert: String): Observable<any> {
    return this.http.post(`${API_ALERT_URL}/${id}?valueAlert=${valueAlert}`, null)
  }

  stopTracking(id: any): Observable<any> {
    return this.http.delete(`${API_ALERT_URL}/${id}`)
  }
  

}
