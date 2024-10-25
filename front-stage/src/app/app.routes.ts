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