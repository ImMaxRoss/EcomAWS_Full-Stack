

# login.component.html
```html
<div class="login-container">

  <!-- Toggle Buttons -->
  <div class="toggle-buttons">
    <button [disabled]="isLoginMode" (click)="toggleMode()">Login</button>
    <button [disabled]="!isLoginMode" (click)="toggleMode()">Register</button>
  </div>

  <!-- Login Form -->
  <div *ngIf="isLoginMode" class="login-section">

    <h2>Login</h2>

    <form (ngSubmit)="onLoginSubmit()" #loginForm="ngForm">
      <div>
        <label for="loginUsername">Username</label>
        <input type="text" id="loginUsername" name="loginUsername" [(ngModel)]="loginUsername" required>
      </div>

      <div>
        <label for="loginPassword">Password</label>
        <input type="password" id="loginPassword" name="loginPassword" [(ngModel)]="loginPassword" required>
      </div>

      <button type="submit" [disabled]="!loginForm.form.valid">Login</button>
    </form>

    <div *ngIf="loginErrorMessage" class="error-message">
      {{ loginErrorMessage }}
    </div>

  </div>

  <!-- Registration Form -->
  <div *ngIf="!isLoginMode" class="registration-section">

    <h2>Register</h2>

    <form (ngSubmit)="onRegisterSubmit()" #registerForm="ngForm">

      <div>
        <label for="registerFirstName">First Name</label>
        <input type="text" id="registerFirstName" name="registerFirstName" [(ngModel)]="registerFirstName" required>
      </div>

      <div>
        <label for="registerLastName">Last Name</label>
        <input type="text" id="registerLastName" name="registerLastName" [(ngModel)]="registerLastName" required>
      </div>

      <div>
        <label for="registerUsername">Username</label>
        <input type="text" id="registerUsername" name="registerUsername" [(ngModel)]="registerUsername" required>
      </div>

      <div>
        <label for="registerEmail">Email</label>
        <input type="email" id="registerEmail" name="registerEmail" [(ngModel)]="registerEmail" required>
      </div>

      <div>
        <label for="registerPassword">Password</label>
        <input type="password" id="registerPassword" name="registerPassword" [(ngModel)]="registerPassword" required>
      </div>

      <div>
        <label for="registerConfirmPassword">Confirm Password</label>
        <input type="password" id="registerConfirmPassword" name="registerConfirmPassword" [(ngModel)]="registerConfirmPassword" required>
      </div>

      <button type="submit" [disabled]="!registerForm.form.valid">Register</button>
    </form>

    <!-- Success or Error Messages -->
    <div *ngIf="registerErrorMessage" class="error-message">
      {{ registerErrorMessage }}
    </div>

    <div *ngIf="registerSuccessMessage" class="success-message">
      {{ registerSuccessMessage }}
    </div>

  </div>

</div>
```

# navbar.component.html
```html
<nav class="navbar">
  <div class="nav-container">
    <!-- Logo aligned to the left -->
    <a routerLink="/" class="logo">T-Shurts</a>

    <!-- Flex container for links and buttons -->
    <div class="flex-grow-container">
      <!-- Centered Products link -->
      <ul class="center-nav">
        <li><a routerLink="/products" class="products-link">Products</a></li>
      </ul>

      <!-- Login/Register/Logout links -->
      <ul class="auth-links">
        <li *ngIf="authService.isLoggedIn()">
          <button (click)="logout()">Logout</button>
        </li>
        <li *ngIf="!authService.isLoggedIn()">
          <a routerLink="/login">Login</a>
        </li>
        <li *ngIf="!authService.isLoggedIn()">
          <a routerLink="/register">Register</a>
        </li>
      </ul>
    </div>

    <!-- Shopping cart aligned to the far right -->
    <ul class="shopping-cart">
      <li>
        <a routerLink="/cart">
          <i class="fa fa-shopping-cart"></i> Cart
        </a>
      </li>
    </ul>
  </div>
</nav>

```

# product.component.html
```html
<div class="product-container">
  <h2>Products</h2>

  <!-- Error Message -->
  <div *ngIf="errorMessage" class="error-message">
    {{ errorMessage }}
  </div>

  <!-- Search Bar -->
  <div class="search-bar">
    <input
      type="text"
      [(ngModel)]="searchQuery"
      (ngModelChange)="onSearch()"
      placeholder="Search products..."
    />
  </div>

  <!-- Product List -->
  <div class="product-list">
    <div *ngFor="let product of filteredProducts" class="product-item">
      <!-- Product Image -->
      <img
        src="/assets/images/{{ product.productId }}.jpg"
        alt="{{ product.productName }}"
        onerror="this.onerror=null; this.src='/assets/images/default.jpg';"
      />

      <!-- Product Details -->
      <div class="product-details">
        <h3>{{ product.productName }}</h3>
        <p>{{ product.description }}</p>
        <p>Price: {{ product.price | currency }}</p>
        <!-- Display discount and special price if available -->
        <p *ngIf="product.discount">Discount: {{ product.discount }}%</p>
        <p *ngIf="product.specialPrice">Special Price: {{ product.specialPrice | currency }}</p>
        <p *ngIf="product.category">Category: {{ product.category }}</p>
        <!-- Add to Cart button -->
        <button (click)="addToCart(product)">Add to Cart</button>
      </div>
    </div>
  </div>
</div>
```

# register.component.html
```html
<div class="register-container">
  <h2>Register</h2>

  <!-- Display Success Message -->
  <div *ngIf="registerSuccessMessage" class="success-message">
    {{ registerSuccessMessage }}
  </div>

  <!-- Display Error Message -->
  <div *ngIf="registerErrorMessage" class="error-message">
    {{ registerErrorMessage }}
  </div>

  <form (ngSubmit)="onRegisterSubmit()" #registerForm="ngForm">
    <!-- First Name -->
    <div class="form-group">
      <label for="firstName">First Name:</label>
      <input 
        type="text" 
        id="firstName" 
        name="firstName" 
        [(ngModel)]="registerFirstName" 
        required>
    </div>

    <!-- Last Name -->
    <div class="form-group">
      <label for="lastName">Last Name:</label>
      <input 
        type="text" 
        id="lastName" 
        name="lastName" 
        [(ngModel)]="registerLastName" 
        required>
    </div>

    <!-- Username -->
    <div class="form-group">
      <label for="username">Username:</label>
      <input 
        type="text" 
        id="username" 
        name="username" 
        [(ngModel)]="registerUsername" 
        required>
    </div>

    <!-- Email -->
    <div class="form-group">
      <label for="email">Email:</label>
      <input 
        type="email" 
        id="email" 
        name="email" 
        [(ngModel)]="registerEmail" 
        required>
    </div>

    <!-- Password -->
    <div class="form-group">
      <label for="password">Password:</label>
      <input 
        type="password" 
        id="password" 
        name="password" 
        [(ngModel)]="registerPassword" 
        required>
    </div>

    <!-- Confirm Password -->
    <div class="form-group">
      <label for="confirmPassword">Confirm Password:</label>
      <input 
        type="password" 
        id="confirmPassword" 
        name="confirmPassword" 
        [(ngModel)]="registerConfirmPassword" 
        required>
    </div>

    <button type="submit" [disabled]="!registerForm.form.valid">Register</button>
  </form>
</div>
```

# shopping-cart.component.html
```html
<h2>Your Shopping Cart</h2>
<div *ngIf="cartItems.length > 0; else emptyCart">
  <div *ngFor="let item of cartItems">
    <div>
      <h3>{{ item.product.productName }}</h3>
      <p>Price: {{ item.productPrice | currency }}</p>
      <p>Quantity: {{ item.quantity }}</p>
      <button (click)="removeItem(item.cartItemId!)">Remove</button>
      <!-- Optionally, add controls to update quantity -->
    </div>
  </div>
</div>

<ng-template #emptyCart>
  <p>Your cart is empty.</p>
</ng-template>
```

# app.component.html
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

# app.routes.ts
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

# app.components.ts
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

# app.config.ts
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

# main.ts
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
    // Other providers if needed
  ],
}).catch((err) => console.error(err));
```