
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../models/user.model';

const API_USERS_URL = 'https://localhost:8080/api/users';

@Injectable({providedIn: 'root'})
export class UserService {

  constructor(private http: HttpClient){}

  public getUsers(): Observable<User[]> {
    return this.http.get<User[]>(API_USERS_URL);
  }

  public updateUser(User: User): Observable<User> {
    return this.http.put<User>(API_USERS_URL, User);
  }

  public deleteUser(userId: string): Observable<void> {
    return this.http.delete<void>(`${API_USERS_URL}/${userId}`);
  }
}