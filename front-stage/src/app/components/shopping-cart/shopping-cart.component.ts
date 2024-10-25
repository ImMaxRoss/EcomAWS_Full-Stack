import { Component, OnInit } from '@angular/core';
import { ShoppingCartService, CartItem } from '../../services/shopping-cart.service';
import { NgForOf, AsyncPipe, NgIf, CurrencyPipe } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-shopping-cart',
  templateUrl: './shopping-cart.component.html',
  styleUrls: ['./shopping-cart.component.css'],
  standalone: true,
  imports: [NgForOf, FormsModule, AsyncPipe, NgIf, CurrencyPipe],
})
export class ShoppingCartComponent implements OnInit {
  cartItems: CartItem[] = [];
  errorMessage: string = '';

  constructor(private shoppingCartService: ShoppingCartService) {}

  ngOnInit(): void {
    this.loadCartItems();
  }

  loadCartItems() {
    this.shoppingCartService.getCartItems().subscribe(
      (items) => {
        this.cartItems = items;
      },
      (error) => {
        console.error('Error fetching cart items:', error);
        this.errorMessage = 'Failed to load cart items. Please try again later.';
      }
    );
  }

  removeItem(cartItemId: number) {
    this.shoppingCartService.removeFromCart(cartItemId).subscribe(
      (response) => {
        console.log('Item removed from cart');
        this.loadCartItems();
      },
      (error) => {
        console.error('Error removing item from cart:', error);
      }
    );
  }

}