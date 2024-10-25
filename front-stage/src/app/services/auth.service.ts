import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';
import { tap } from 'rxjs/operators';

export interface User {
  userId?: number;
  firstName: string;
  lastName: string;
  username: string;
  email: string;
  password: string;
  role?: string;
}

export interface AuthenticationRequest {
  username: string;
  password: string;
}

export interface AuthenticationResponse {
  token: string;
  userId: number;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
            // EC2 IP = 98.84.36.177
  
  // private baseUrl = "http://localhost:8080/api";
  private baseUrl = "http://98.84.36.177:8080/api";
  private currentUserSubject: BehaviorSubject<AuthenticationResponse | null>;
  public currentUser: Observable<AuthenticationResponse | null>;

  constructor(private http: HttpClient) {
    const storedToken = this.getLocalStorageItem('currentUserToken');
    const storedUserId = this.getLocalStorageItem('userId');
  
    this.currentUserSubject = new BehaviorSubject<AuthenticationResponse | null>(
      storedToken && storedUserId ? { token: storedToken, userId: Number(storedUserId) } : null
    );
  
    this.currentUser = this.currentUserSubject.asObservable();
  }

  public getUserId(): number | null {
    const currentUser = this.currentUserSubject.value;
    return currentUser ? currentUser.userId : null;
  }


  public get currentUserValue(): AuthenticationResponse | null {
    const currentUser = this.currentUserSubject.value;
    if (!currentUser) {
      const storedToken = this.getLocalStorageItem('currentUserToken');
      const storedUserId = this.getLocalStorageItem('userId');
      if (storedToken && storedUserId) {
        return { token: storedToken, userId: Number(storedUserId) };
      }
      return null;
    }
    return currentUser;
  }


  login(request: AuthenticationRequest): Observable<AuthenticationResponse> {
    return this.http.post<AuthenticationResponse>(`${this.baseUrl}/login`, request)
      .pipe(
        tap(response => {
          if (response && response.token) {
            this.setLocalStorageItem('currentUserToken', response.token);
            this.setLocalStorageItem('userId', response.userId.toString()); // Potential issue here if userId is undefined
            this.currentUserSubject.next(response);
          }
        })
      );
  }

  register(user: User): Observable<AuthenticationResponse> {
    return this.http.post<AuthenticationResponse>(`${this.baseUrl}/register`, user)
      .pipe(
        tap(response => {
          if (response && response.token) {
            this.setLocalStorageItem('currentUserToken', response.token);
            this.setLocalStorageItem('userId', response.userId.toString()); // Store userId
            this.currentUserSubject.next(response);
          }
        })
      );
  }
  
  logout(): void {
    this.removeLocalStorageItem('currentUserToken');
    this.currentUserSubject.next(null);
  }

  isLoggedIn(): boolean {
    return !!this.currentUserSubject.value;
  }

  getToken(): string | null {
    const currentUser = this.currentUserSubject.value;
    return currentUser ? currentUser.token : null;
  }

  private getLocalStorageItem(key: string): string | null {
    return typeof window !== 'undefined' ? localStorage.getItem(key) : null;
  }

  private setLocalStorageItem(key: string, value: string): void {
    if (typeof window !== 'undefined') {
      localStorage.setItem(key, value);
    }
  }

  private removeLocalStorageItem(key: string): void {
    if (typeof window !== 'undefined') {
      localStorage.removeItem(key);
    }
  }

}