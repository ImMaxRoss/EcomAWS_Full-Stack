

## main.ts
```typescript
// main.ts
import { enableProdMode } from '@angular/core';
import { bootstrapApplication } from '@angular/platform-browser';
import { AppComponent } from './app/app.component';
import { environment } from './environments/environment'; // Import the environment
import { provideRouter } from '@angular/router';
import { routes } from './app/app.routes';
import { provideHttpClient } from '@angular/common/http';
import { importProvidersFrom } from '@angular/core';
import { FormsModule } from '@angular/forms';

if (environment.production) {
  enableProdMode();
}

bootstrapApplication(AppComponent, {
  providers: [
    provideRouter(routes),
    provideHttpClient(),
    importProvidersFrom(FormsModule),

  ],
}).catch((err) => console.error(err));
```
## index.html
```html
<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <title>StageFront</title>
  <base href="/">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="icon" type="image/x-icon" href="favicon.ico">
</head>
<body>
  <app-root></app-root>
</body>
</html>

```

## app.routes.ts
```typescript
import { Routes } from '@angular/router';
import { RegisterComponent } from './components/register/register.component';
import { AuthLoginComponent } from './components/login/login.component';
import { ProductComponent } from './components/product/product.component';
import { AuthGuard } from './services/auth.guard';
import { ShoppingCartComponent } from './components/shopping-cart/shopping-cart.component';


export const routes: Routes = [
  { path: '', redirectTo: '/products', pathMatch: 'full' },
  { path: 'login', component: AuthLoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'products', component: ProductComponent, canActivate: [AuthGuard]  },
  { path: 'cart', component: ShoppingCartComponent, canActivate: [AuthGuard] },
];
```
## app.config.ts
```typescript
import { ApplicationConfig, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';
import { provideHttpClient, withFetch } from '@angular/common/http';

import { routes } from './app.routes';

export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes),
    provideHttpClient(withFetch())
  ]
};
```

## app.component.ts
```typescript
import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { NavbarComponent } from './components/navbar/navbar.component';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  standalone: true,
  imports: [RouterOutlet, NavbarComponent, RouterLink],
})
export class AppComponent {
  title = 'Your Application';
}

```
## app.component.html
```html
<!-- app.component.html -->
<app-navbar></app-navbar>

<main class="main-content">
  <div class="sidebar">
    <!-- SVG Logo -->
    <svg
      xmlns="http://www.w3.org/2000/svg"
      viewBox="0 0 982 239"
      fill="none"
      class="angular-logo"
    >
      <!-- SVG content here -->
    </svg>
    <h1>Welcome to T-Shurts</h1>
    <p>Your destination for custom T-shirts</p>
  </div>
  <div class="content-area">
    <router-outlet></router-outlet>
  </div>
</main>

```

## auth.service.ts
```typescript
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
```

## product.service.ts
```typescript
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

import { AuthService } from '../services/auth.service';

export interface Product {
  productId: number;
  productName: string;
  description: string;
  quantity: number;
  price: number;
  discount?: number;
  specialPrice?: number;
  category?: string;
}

@Injectable({
  providedIn: 'root',
})
export class ProductService {
  private baseUrl = 'http://98.84.36.177:8080/api/products';
  // private baseUrl = 'http://localhost:8080/api/products';

  constructor(private http: HttpClient, private authService: AuthService) {}


  getProducts(): Observable<Product[]> {
    const token = this.authService.getToken();
    let headers = new HttpHeaders();

    if (token) {
      headers = headers.set('Authorization', 'Bearer ' + token);
    }

    return this.http.get<Product[]>(`${this.baseUrl}`, { headers: headers });
  }
}
```

## shopping-cart.service.ts
```typescript
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http'; 
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';
import { Product } from './product.service';
import { environment } from '../../environments/environment';

export interface CartItem {
  cartItemId?: number;
  product: Product;
  quantity: number;
  discount?: number;
  productPrice: number;
}

@Injectable({
  providedIn: 'root',
})
export class ShoppingCartService {
  // private cartUrl = "http://localhost:8080/api/carts";
  private cartUrl = "http://98.84.36.177:8080/api/carts";

  constructor(private http: HttpClient, private authService: AuthService) {}

  addToCart(productId: number, quantity: number): Observable<CartItem> {
    const token = this.authService.getToken();
    let headers = new HttpHeaders().set('Content-Type', 'application/json');
  
    if (token) {
      headers = headers.set('Authorization', 'Bearer ' + token);
    }
  
    const body = {
      productId: productId,
      quantity: quantity,
    };
  
    return this.http.post<CartItem>(`${this.cartUrl}/add`, body, { headers });
  }

  getCartItems(): Observable<CartItem[]> {
    const token = this.authService.getToken();
    let headers = new HttpHeaders();

    if (token) {
      headers = headers.set('Authorization', 'Bearer ' + token);
    }

    return this.http.get<CartItem[]>(`${this.cartUrl}/cart/user`, { headers });
  }

  removeFromCart(cartItemId: number): Observable<any> {
    const token = this.authService.getToken();
    let headers = new HttpHeaders();

    if (token) {
      headers = headers.set('Authorization', 'Bearer ' + token);
    }

    return this.http.delete(`${this.cartUrl}/${cartItemId}`, { headers });
  }

}
```

## login.component.ts
```typescript

```
## auth.service.html
```html

```

## login.component.ts
```typescript

```
## login.component.html
```html

```

## register.component.ts
```typescript

```
## register.component.html
```html

```

## product.component.ts
```typescript

```
## product.component.html
```html

```

## navbar.component.ts
```typescript

```
## navbar.component.html
```html

```

## shopping-cart.component.ts
```typescript

```
## shopping-cart.component.html
```html

```