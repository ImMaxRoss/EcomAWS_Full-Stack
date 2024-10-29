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
  // private baseUrl = 'http://98.84.36.177:8080/api/products';
  private baseUrl = 'http://localhost:8080/api/products';

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