import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Item } from '../models/item.model';

const API_URL = 'https://localhost:8080/api/items';
const addToListURL = 'https://localhost:8080/api/wishlist';

@Injectable({
  providedIn: 'root'
})
export class ItemService {

  constructor(private http: HttpClient) { }

  getAll(params: any): Observable<any> {
    return this.http.get<any>(API_URL, { params });
  }

  get(id: any): Observable<Item> {
    return this.http.get(`${API_URL}/${id}`);
  }

  create(data: any): Observable<any> {
    return this.http.post(API_URL, data);
  }

  update(id: any, data: any): Observable<any> {
    return this.http.put(`${API_URL}/${id}`, data);
  }

  delete(id: any): Observable<any> {
    return this.http.delete(`${API_URL}/${id}`);
  }

  deleteAll(): Observable<any> {
    return this.http.delete(API_URL);
  }

  findByName(name: any): Observable<Item[]> {
    return this.http.get<Item[]>(`${API_URL}?name=${name}`);
  }

  addToList(id: String): Observable<any> {
    return this.http.post(`${addToListURL}`, id );
  }
}