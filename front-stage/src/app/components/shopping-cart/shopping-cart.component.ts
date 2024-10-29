import { Component, OnInit } from '@angular/core';
import { ShoppingCartService, CartItem } from '../../services/shopping-cart.service';
import { NgForOf, AsyncPipe, NgIf, CurrencyPipe, JsonPipe } from '@angular/common'; // Import JsonPipe
import { FormsModule } from '@angular/forms';
import { Product } from '../../services/product.service';
import { AuthService } from '../../services/auth.service'; // Import AuthService

@Component({
  selector: 'app-shopping-cart',
  templateUrl: './shopping-cart.component.html',
  styleUrls: ['./shopping-cart.component.css'],
  standalone: true,
  imports: [NgForOf, FormsModule, AsyncPipe, NgIf, CurrencyPipe, JsonPipe], // Add JsonPipe to imports
})
export class ShoppingCartComponent implements OnInit {
  cartItems: CartItem[] = [];
  errorMessage: string = '';

  constructor(private shoppingCartService: ShoppingCartService, private authService: AuthService) {} // Inject AuthService

  ngOnInit(): void {
    this.loadCartItems();
  }

  loadCartItems() {
    console.log('loadCartItems called');
  
    const userId = this.authService.getUserId();
    console.log('Retrieved userId:', userId);
  
    if (userId) {
      this.shoppingCartService.getCartItems(userId).subscribe(
        (items) => {
          console.log('Cart items retrieved:', items);
          this.cartItems = items;
        },
        (error) => {
          console.error('Error fetching cart items:', error);
          this.errorMessage = 'Failed to load cart items. Please try again later.';
        }
      );
    } else {
      console.error('No userId found');
    }
  }

  addToCart(product: Product) {
    this.shoppingCartService.addToCart(product.productId, 1).subscribe(
      (cartItem) => {
        console.log('Product added to cart:', cartItem);
        // Optionally display a success message or update cart count
      },
      (error) => {
        console.error('Error adding product to cart:', error);
        // Handle error (e.g., display an error message)
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