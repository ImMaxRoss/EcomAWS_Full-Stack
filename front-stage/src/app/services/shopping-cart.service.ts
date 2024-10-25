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