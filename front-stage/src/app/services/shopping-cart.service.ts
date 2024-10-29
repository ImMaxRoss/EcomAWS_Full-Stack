import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http'; 
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';
import { Product } from './product.service';

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
  private cartUrl = "http://localhost:8080/api/carts";

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
  
    return this.http.post<CartItem>(`${this.cartUrl}/items/add`, body, { headers });
  }

  getCartItems(userId: number): Observable<CartItem[]> {
    const token = this.authService.getToken();
    let headers = new HttpHeaders();

    if (token) {
      headers = headers.set('Authorization', 'Bearer ' + token);
    }

    return this.http.get<CartItem[]>(`${this.cartUrl}/items/cart/${userId}`, { headers });
  }

  removeFromCart(cartItemId: number): Observable<any> {
    const token = this.authService.getToken();
    let headers = new HttpHeaders();

    if (token) {
      headers = headers.set('Authorization', 'Bearer ' + token);
    }

    return this.http.delete(`${this.cartUrl}/items/${cartItemId}`, { headers });
  }
}