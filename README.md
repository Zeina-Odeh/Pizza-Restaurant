# Pizza Restaurant Android Application

## Project Description
An Android app for a pizza restaurant allowing users to order pizza online or via a local database. The app is user-friendly and simple, including:

### Introduction Layout
### Login and Registration
### Customer Home Layout
### Admin Home Layout
### Extra Features

## Functionalities

### 1. Introduction Layout
- **Get Started Button:** Connects to a server using REST to load pizza types.
  - **Success:** Redirects to login and registration.
  - **Failure:** Shows an error message.
- **Pizza Types URL:** [Pizza Types]([https://androidproject.free.beeceptor.com/pizza1])

### 2. Login and Registration
- **Login:** Redirects to the login page.
- **Login Page:** Email and password input with "Remember Me" checkbox.
- **Sign Up:** Redirects to the registration page.
- **Registration Page:** User details with validations (email, phone, name, gender, password).

### 3. Customer Home Layout
- **Navigation Drawer Activity:**
  - **Home:** Restaurant history.
  - **Pizza Menu:** Lists pizza types with search, add to favorites, and order options.
  - **Your Orders:** Lists previous orders.
  - **Your Favorites:** Lists favorite pizzas with ordering.
  - **Special Offers:** Lists offers with ordering.
  - **Profile:** View and edit personal information.
  - **Call Us or Find Us:** Call, map, and email options.
  - **Logout:** Logs out and redirects to login.

### 4. Admin Home Layout
- **Navigation Drawer Activity:**
  - **Admin Profile:** View and edit personal information.
  - **Add Admin:** Add new admins.
  - **View All Orders:** Lists all orders with details.
  - **Add Special Offers:** Add new offers.
  - **Logout:** Logs out and redirects to login.

### 5. Extra Features
- **Profile Picture:** Add and change profile pictures.
- **Admin Analytics:** Calculate orders and income for each pizza type and overall.

## Technical Requirements
- **Android Layouts:** Dynamic and static.
- **Intents & Notifications:** Toast messages.
- **SQLite Database:** User and order storage.
- **Animation:** Frame or tween.
- **Fragments:** Display sections.
- **Shared Preferences:** Save user preferences.
- **REST:** Server communication.

## Getting Started
1. Clone the repository: `git clone <repository_url>`
2. Open in Android Studio.
3. Set up dependencies and configurations.
4. Run on an emulator or device.
