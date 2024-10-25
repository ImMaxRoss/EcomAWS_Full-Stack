import { Component, OnInit } from '@angular/core';
import { ProductService, Product } from '../../services/product.service';
import { FormsModule } from '@angular/forms';
import { NgForOf, AsyncPipe, NgIf } from '@angular/common';
import { ShoppingCartService } from '../../services/shopping-cart.service';
import { CurrencyPipe } from '@angular/common';

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css'],
  standalone: true,
  imports: [NgForOf, FormsModule, AsyncPipe, NgIf, CurrencyPipe],
})
export class ProductComponent implements OnInit {
  products: Product[] = [];
  filteredProducts: Product[] = [];
  searchQuery: string = '';
  errorMessage: string = '';

  constructor(
    private productService: ProductService,
    private shoppingCartService: ShoppingCartService
  ) {}

  ngOnInit(): void {
    // Fetch products on component initialization
    this.productService.getProducts().subscribe(
      (data: Product[]) => {
        this.products = data;
        this.filteredProducts = data; // Initialize filtered products with all products
      },
      (error) => {
        console.error('Error fetching products:', error);
        this.errorMessage = 'Failed to load products. Please try again later.';
      }
    );
  }

  // Method to filter products based on search query
  onSearch() {
    const query = this.searchQuery.toLowerCase();

    if (query === '') {
      // If query is empty, show all products
      this.filteredProducts = this.products;
    } else {
      // Filter products based on the query
      this.filteredProducts = this.products.filter((product) =>
        product.productName.toLowerCase().includes(query)
      );
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
}