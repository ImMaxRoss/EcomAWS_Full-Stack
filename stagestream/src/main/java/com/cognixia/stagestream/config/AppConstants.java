package com.cognixia.stagestream.config;

public class AppConstants {

    public static final String[] PUBLIC_URLS = {
        "/swagger-ui/**",
        "/v3/api-docs/**",
        "/api/register/**",
        "/api/login"
    };
    public static final String[] USER_URLS = {"/api/user/**"};
    public static final String[] ADMIN_URLS = {"/api/admin/**", "/api/products/**"};
    public static final String USER_ROLE = "USER";
    public static final String ADMIN_ROLE = "ADMIN";

    public static final String REGISTER_URL = "/api/register";
    public static final String LOGIN_URL = "/api/login";
    public static final String USER_BASE_URL = "/api/user";

    public static final String PRODUCT_BASE_URL = "/api/products";
    public static final String CART_BASE_URL = "/api/carts";
    public static final String CART_ITEMS_URL = "/api/carts/items";

}