# E-commerce Website

This is an e-commerce website that utilizes Java and the Spring Boot environment to create a robust backend, supporting payments with the Stripe API. The backend includes an inventory administration panel, secure user profiles, and checkout functionality. Here's what you can do on this platform:

1. Sign up/sign in.
2. Fetch, add, or update products.
3. Fetch, add, or update product categories.
4. Create a wishlist for your favorite products.
5. View product details of your orders or checked-out items.

## Running the Application Locally

To run the program locally, simply execute the main class of the application. The database is an in-memory H2 database, so there's no need to set up an additional database. You can access all the APIs through Swagger at the following URL:

``` http://localhost:8080/swagger-ui/index.html#/ ```

Alternatively, you can create a Docker image using the command:

```bash
docker build -t ecommerce-test .
