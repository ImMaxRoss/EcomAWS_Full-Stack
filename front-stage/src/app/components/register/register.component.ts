import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms'; 
import { AuthService, User, AuthenticationResponse } from '../../services/auth.service'; 
import { NgIf } from '@angular/common';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
  standalone: true,
  imports: [FormsModule, NgIf],
})
export class RegisterComponent {

  // Registration Form Fields
  registerFirstName: string = '';
  registerLastName: string = '';
  registerUsername: string = '';
  registerEmail: string = '';
  registerPassword: string = '';
  registerConfirmPassword: string = '';

  // Feedback Messages
  registerErrorMessage: string = '';
  registerSuccessMessage: string = '';

  constructor(
    private authService: AuthService,
    private router: Router
  ) { }

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
        this.registerSuccessMessage = 'Registration successful! Redirecting to login...';
        // Navigate to the login page after successful registration
        setTimeout(() => {
          this.router.navigate(['/login']); // Adjust the route as needed
        }, 2000); // Optional delay before redirecting
      },
      (error) => {
        // Display error message for registration failure
        if (error && error.error && error.error.message) {
          this.registerErrorMessage = error.error.message;
        } else {
          this.registerErrorMessage = 'Registration failed. Please try again.';
        }
      }
    );
  }

}