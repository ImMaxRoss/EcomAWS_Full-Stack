import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms'; 
import { AuthService, AuthenticationRequest, AuthenticationResponse, User } from '../../services/auth.service'; 
import { NgIf } from '@angular/common';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  standalone: true,
  imports: [FormsModule, NgIf],
})
export class AuthLoginComponent implements OnInit {

  // Toggle between login and registration views
  isLoginMode: boolean = true;

  // Login Form Fields
  loginUsername: string = '';
  loginPassword: string = '';

  // Registration Form Fields
  registerFirstName: string = '';
  registerLastName: string = '';
  registerUsername: string = '';
  registerEmail: string = '';
  registerPassword: string = '';
  registerConfirmPassword: string = '';

  // Feedback Messages
  loginErrorMessage: string = '';
  registerErrorMessage: string = '';
  registerSuccessMessage: string = '';

  constructor(
    private authService: AuthService,
    private router: Router
  ) { }

  ngOnInit(): void {
    // Any initialization logic can go here
  }

  // Switch between login and registration
  toggleMode() {
    this.isLoginMode = !this.isLoginMode;
    // Clear messages when switching modes
    this.loginErrorMessage = '';
    this.registerErrorMessage = '';
    this.registerSuccessMessage = '';
  }

  // Handle login form submission
  onLoginSubmit() {
    const request: AuthenticationRequest = {
      username: this.loginUsername,
      password: this.loginPassword
    };

    this.authService.login(request).subscribe(
      (response: AuthenticationResponse) => {
        // Navigate to the product viewing page upon successful login
        this.router.navigate(['/products']); // Adjust the route as needed
      },
      (error) => {
        // Display error message for incorrect credentials
        this.loginErrorMessage = 'Incorrect username or password.';
      }
    );
  }

  // Handle registration form submission
  onRegisterSubmit() {
    // Check if passwords match
    if (this.registerPassword !== this.registerConfirmPassword) {
      this.registerErrorMessage = 'Passwords do not match.';
      return;
    }

    const newUser: User = {
      firstName: this.registerFirstName,
      lastName: this.registerLastName,
      username: this.registerUsername,
      email: this.registerEmail,
      password: this.registerPassword
    };

    this.authService.register(newUser).subscribe(
      (response: AuthenticationResponse) => {
        // Display success message and optionally navigate to another page
        this.registerSuccessMessage = 'Registration successful! Redirecting to products...';
        // Navigate to the product viewing page
        this.router.navigate(['/products']); // Adjust the route as needed
      },
      (error) => {
        // Display error message for registration failure
        this.registerErrorMessage = 'Registration failed. Please try again.';
      }
    );
  }

}