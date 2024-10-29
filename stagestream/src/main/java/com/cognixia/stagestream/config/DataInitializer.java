// package com.cognixia.stagestream.config;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.CommandLineRunner;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.crypto.password.PasswordEncoder;

// import com.cognixia.stagestream.models.User;
// import com.cognixia.stagestream.models.Product;
// import com.cognixia.stagestream.repositories.UserRepository;
// import com.cognixia.stagestream.repositories.ProductRepository;

// import java.util.Arrays;
// import java.util.List;

// @Configuration
// public class DataInitializer {

//     @Autowired
//     private PasswordEncoder passwordEncoder;

//     @Autowired
//     private ProductRepository productRepository;

//     @Bean
//     public CommandLineRunner initAdminUser(UserRepository userRepository) {
//         return args -> {
//             if (!userRepository.existsByUsername("admin")) {
//                 User adminUser = new User();
//                 adminUser.setFirstName("Admin");
//                 adminUser.setLastName("User");
//                 adminUser.setUsername("admin");
//                 adminUser.setEmail("admin@example.com");
//                 adminUser.setRole(AppConstants.ADMIN_ROLE);
//                 adminUser.setPassword(passwordEncoder.encode("adminpassword123"));
//                 userRepository.save(adminUser);
//                 System.out.println("Admin user created.");
//             }
//         };
//     }

//     @Bean
//     public CommandLineRunner initProducts() {
//         return args -> {
//             if (productRepository.count() == 0) {
//                 // Create product instances
//                 Product product1 = new Product(null, "Razzle Dazzle Shirt",
//                         "Classic Tie Die Rainbow Swirls with bedazzling",
//                         10, 29.99, 0.0, 29.99, "Tie-Die", null, null);

//                 Product product2 = new Product(null, "Vintage Graphic Tee",
//                         "Retro graphic print on a soft cotton blend",
//                         10, 24.99, 0.0, 24.99, "Graphic", null, null);

//                 Product product3 = new Product(null, "Plain White Tee",
//                         "Simple and classic white t-shirt",
//                         10, 15.99, 0.0, 15.99, "Basic", null, null);

//                 Product product4 = new Product(null, "Sports Performance Shirt",
//                         "Moisture-wicking t-shirt for athletes",
//                         10, 19.99, 0.0, 19.99, "Sports", null, null);

//                 Product product5 = new Product(null, "V-Neck Casual Shirt",
//                         "Casual v-neck t-shirt for everyday wear",
//                         10, 18.99, 0.0, 18.99, "Casual", null, null);

//                 Product product6 = new Product(null, "Striped Polo Shirt",
//                         "Classic striped polo with a comfortable fit",
//                         10, 32.99, 0.0, 32.99, "Polo", null, null);

//                 Product product7 = new Product(null, "Henley Long Sleeve",
//                         "Comfortable henley shirt with button placket",
//                         10, 27.99, 0.0, 27.99, "Long Sleeve", null, null);

//                 Product product8 = new Product(null, "Floral Print Tee",
//                         "Bright floral print on a soft cotton tee",
//                         10, 22.99, 0.0, 22.99, "Floral", null, null);

//                 Product product9 = new Product(null, "Pocket Tee",
//                         "T-shirt with a stylish chest pocket",
//                         10, 17.99, 0.0, 17.99, "Pocket", null, null);

//                 Product product10 = new Product(null, "Raglan Baseball Shirt",
//                         "Raglan sleeve design for a sporty look",
//                         10, 21.99, 0.0, 21.99, "Baseball", null, null);

//                 List<Product> products = Arrays.asList(
//                         product1, product2, product3, product4, product5,
//                         product6, product7, product8, product9, product10
//                 );

//                 productRepository.saveAll(products);

//                 System.out.println("Products initialized.");
//             }
//         };
//     }
// }