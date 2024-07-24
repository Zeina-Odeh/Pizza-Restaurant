package com.example.advancepizza_1190083_1190113;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataBaseHelper extends android.database.sqlite.SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Pizza_Restaurant.db";
    private static final int DATABASE_VERSION = 21;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("Database", "onCreate called");
        String createAdminTable = "CREATE TABLE Admin (" +
                "email TEXT PRIMARY KEY," +
                "password_hash TEXT," +
                "first_name TEXT," +
                "last_name TEXT," +
                "gender TEXT," +
                "phone TEXT," +
                "photo BLOB," +
                "user_type INTEGER);";

        String createCustomerTable = "CREATE TABLE Customer (" +
                "email TEXT PRIMARY KEY," +
                "password_hash TEXT," +
                "first_name TEXT," +
                "last_name TEXT," +
                "gender TEXT," +
                "phone TEXT," +
                "photo BLOB," +
                "user_type INTEGER);";

        String createPizzaTable = "CREATE TABLE Pizza (" +
                "id INTEGER PRIMARY KEY," +
                "type TEXT," +
                "category TEXT," +
                "price DOUBLE);";

        String createOrderTable = "CREATE TABLE Ordered (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "pizzaType TEXT," +
                "customerEmail TEXT," +
                "orderDate TEXT," +
                "orderTime TEXT," +
                "size TEXT," +
                "quantity INTEGER," +
                "basePrice REAL," +
                "finalPrice REAL);";

        String createFavoritesTable = "CREATE TABLE Favorites (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "pizzaID INTEGER," +
                "customerEmail TEXT);";

        String createSpecialOffersTable = "CREATE TABLE SpecialOffers (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "type TEXT," +
                "size TEXT," +
                "offer_period TEXT," +
                "startDateTime TEXT," +
                "endDateTime TEXT," +
                "total_price DOUBLE);";

        db.execSQL(createAdminTable);
        db.execSQL(createCustomerTable);
        db.execSQL(createPizzaTable);
        db.execSQL(createOrderTable);
        db.execSQL(createFavoritesTable);
        db.execSQL(createSpecialOffersTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("Database", "onUpgrade called from " + oldVersion + " to " + newVersion);

        db.execSQL("DROP TABLE IF EXISTS Admin");
        db.execSQL("DROP TABLE IF EXISTS Customer");
        db.execSQL("DROP TABLE IF EXISTS Pizza");
        db.execSQL("Drop TABLE IF EXISTS Ordered");
        db.execSQL("Drop TABLE IF EXISTS Favorites");
        db.execSQL("DROP TABLE IF EXISTS SpecialOffers");
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("Database", "onDowngrade called from " + oldVersion + " to " + newVersion);

        db.execSQL("DROP TABLE IF EXISTS Admin");
        db.execSQL("DROP TABLE IF EXISTS Customer");
        db.execSQL("DROP TABLE IF EXISTS Pizza");
        db.execSQL("DROP TABLE IF EXISTS Ordered");
        db.execSQL("DROP TABLE IF EXISTS Favorites");
        db.execSQL("DROP TABLE IF EXISTS SpecialOffers");
        onCreate(db);
    }

    public boolean insertPizza(Pizza pizza) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d("Database", "Inserting pizza with ID: " + pizza.getId());

        // Check if the pizza already exists in the database based on its ID
        Cursor cursor = db.rawQuery("SELECT * FROM Pizza WHERE id = ?", new String[]{String.valueOf(pizza.getId())});
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.close();
                db.close();
                Log.d("Database", "Pizza already exists in the database with ID: " + pizza.getId());
                return false;
            }
            cursor.close();
        } else {
            Log.d("Database", "Cursor is null while checking for pizza existence.");
        }

        ContentValues values = new ContentValues();
        values.put("id", pizza.getId());
        values.put("type", pizza.getType());
        values.put("category", pizza.getCategory());
        values.put("price", pizza.getPrice());
        //values.put("offer", pizza.getOffer());

        // Insert the values into the "Pizza" table
        long result = db.insert("Pizza", null, values);

        // Close the database
        db.close();

        // If insertion is successful then result != -1
        if (result != -1) {
            Log.d("Database", "Pizza inserted successfully with ID: " + pizza.getId());
            return true;
        } else {
            // Error inserting pizza
            Log.d("Database", "Error inserting pizza with ID: " + pizza.getId());
            return false;
        }
    }

    public boolean insertCustomer(Customer customer) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("email", customer.getEmail());
        contentValues.put("password_hash", customer.getPassword());
        contentValues.put("first_name", customer.getFirstName());
        contentValues.put("last_name", customer.getLastName());
        contentValues.put("gender", customer.getGender());
        contentValues.put("phone", customer.getPhone());
        contentValues.put("photo", customer.getPhoto());
        contentValues.put("user_type", customer.getUserType());

        // If insertion is successful then result != -1
        long result = db.insert("Customer", null, contentValues);
        db.close();
        return result != -1;
    }

    public boolean insertAdmin(Admin admin) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("email", admin.getEmail());
        contentValues.put("password_hash", admin.getPassword());
        contentValues.put("first_name", admin.getFirstName());
        contentValues.put("last_name", admin.getLastName());
        contentValues.put("gender", admin.getGender());
        contentValues.put("phone", admin.getPhone());
        contentValues.put("photo", admin.getPhoto());
        contentValues.put("user_type", admin.getUserType());

        // If insertion is successful then result != -1
        long result = db.insert("Admin", null, contentValues);
        db.close();
        return result != -1;
    }

    public Admin getAdminByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("Admin", null, "email=?", new String[]{email}, null, null, null);

        if (cursor != null) {
            Log.d("DatabaseHelper", "Column names: " + Arrays.toString(cursor.getColumnNames()));
            if (cursor.moveToFirst()) {
                @SuppressLint("Range") String fetchedEmail = cursor.getString(cursor.getColumnIndex("email"));
                @SuppressLint("Range") String fetchedPassword = cursor.getString(cursor.getColumnIndex("password_hash"));
                @SuppressLint("Range") String firstName = cursor.getString(cursor.getColumnIndex("first_name"));
                @SuppressLint("Range") String lastName = cursor.getString(cursor.getColumnIndex("last_name"));
                @SuppressLint("Range") String gender = cursor.getString(cursor.getColumnIndex("gender"));
                @SuppressLint("Range") String phone = cursor.getString(cursor.getColumnIndex("phone"));
                @SuppressLint("Range") byte[] photo = cursor.getBlob(cursor.getColumnIndex("photo"));
                @SuppressLint("Range") int userType = cursor.getInt(cursor.getColumnIndex("user_type"));

                cursor.close();
                return new Admin(fetchedEmail, fetchedPassword, firstName, lastName, gender, phone, photo);
            }
            cursor.close();
        }
        return null;
    }


    public int validateUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        // Check in each table (Admin, and Customer) one by one.
        // Assuming the user_type is stored as 0 for Admin, 1 for Customer, so after the i+1 it
        // will give the right user_type
        String[] tableNames = {"Admin", "Customer"};

        for (int i = 0; i < tableNames.length; i++) {
            if (i == 0) {
                Cursor cursor = db.rawQuery("SELECT * FROM " + "Admin" + " WHERE email=? AND password_hash=?", new String[]{email, password});
                if (cursor.getCount() > 0) {
                    return i + 1;
                }
                cursor.close();
            } else if (i == 1) {
                Cursor cursor = db.rawQuery("SELECT * FROM " + "Customer" + " WHERE email=? AND password_hash=?", new String[]{email, password});
                if (cursor.getCount() > 0) {
                    return i + 1;
                }
                cursor.close();
            }
        }
        return -1;
    }

    public boolean emailExists(String table, String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + table + " WHERE email=?", new String[]{email});
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    @SuppressLint("Range")
    public String getUsername(String email) {
        String first = null, last = null;
        SQLiteDatabase db = this.getWritableDatabase();

        // Use rawQuery with parameters to avoid SQL injection
        Cursor cursor = db.rawQuery("SELECT first_name, last_name FROM Admin WHERE email = ?" +
                " UNION ALL " +
                "SELECT first_name, last_name FROM Customer WHERE email = ?", new String[]{email, email});

        if (cursor.moveToFirst()) {
            first = cursor.getString(cursor.getColumnIndex("first_name"));
            last = cursor.getString(cursor.getColumnIndex("last_name"));
        }

        // Close the cursor to free up resources
        cursor.close();

        // Close the database connection
        db.close();

        // Check if both first and last names are null, indicating that the email doesn't exist
        if (first == null && last == null) {
            return null; // or some default value indicating that the email doesn't exist
        }

        // Return the formatted username
        return first + " " + last;
    }

    public Cursor displayProfileData(String email) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Use rawQuery with parameters to avoid SQL injection
        return db.rawQuery("SELECT * FROM Admin WHERE email = ?" +
                " UNION ALL " +
                "SELECT * FROM Customer WHERE email = ?", new String[]{email, email});
    }

    @SuppressLint("Range")
    public int getUserType(String email) {
        int userType = -1;
        SQLiteDatabase db = this.getWritableDatabase();

        // Use rawQuery with parameters to avoid SQL injection
        Cursor cursor = db.rawQuery("SELECT user_type FROM Admin WHERE email = ?" +
                " UNION ALL " +
                "SELECT user_type FROM Customer WHERE email = ?", new String[]{email, email});

        if (cursor.moveToFirst()) {
            userType = cursor.getInt(cursor.getColumnIndex("user_type"));
        }

        // Close the cursor to free up resources
        cursor.close();

        // Return the user type
        return userType;
    }

    @SuppressLint("Range")
    public byte[] getPhoto(String email) {
        byte[] photo = new byte[0];

        SQLiteDatabase db = this.getWritableDatabase();

        // Use rawQuery with parameters to avoid SQL injection
        Cursor cursor = db.rawQuery("SELECT photo FROM Admin WHERE email = ?" +
                " UNION ALL " +
                "SELECT photo FROM Customer WHERE email = ?", new String[]{email, email});

        if (cursor.moveToFirst()) {
            photo = cursor.getBlob(cursor.getColumnIndex("photo"));
        }

        // Close the cursor to free up resources
        cursor.close();
        // Return the user type

        return photo;
    }


    public boolean updateAdmin(String email, Admin admin) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("first_name", admin.getFirstName());
        values.put("last_name", admin.getLastName());
        values.put("password_hash", admin.getPassword());
        values.put("phone", admin.getPhone());

        // Specify the WHERE clause to update the specific row
        String whereClause = "email=?";
        String[] whereArgs = {email};

        // Update the Admin table
        int numRowsAffected = db.update("Admin", values, whereClause, whereArgs);

        // Close the database connection
        db.close();

        // Check if the update was successful
        return numRowsAffected > 0;
    }

    public boolean updateCustomer(String email, Customer customer) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("first_name", customer.getFirstName());
        values.put("last_name", customer.getLastName());
        values.put("password_hash", customer.getPassword());
        values.put("phone", customer.getPhone());

        // Specify the WHERE clause to update the specific row
        String whereClause = "email=?";
        String[] whereArgs = {email};

        // Update the Customer table
        int numRowsAffected = db.update("Customer", values, whereClause, whereArgs);

        // Close the database connection
        db.close();

        // Check if the update was successful
        return numRowsAffected > 0;
    }

    public boolean updatePhoto(String email, int userType, byte[] newPhoto) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("photo", newPhoto);  // Updated photo

        // Specify the WHERE clause to update the specific row
        String whereClause = "email=?";
        String[] whereArgs = {email};
        int numRowsAffected=0;
        if(userType == 1)
            // Update the Customer table
            numRowsAffected = db.update("Admin", values, whereClause, whereArgs);
        else if (userType==2) {
            numRowsAffected = db.update("Customer", values, whereClause, whereArgs);

        }

        // Close the database connection
        db.close();

        // Check if the update was successful
        return numRowsAffected > 0;
    }



    public boolean insertOrder(Ordered order) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("pizzaType", order.getPizzaType());
        values.put("customerEmail", order.getCustomerEmail());
        values.put("orderDate", order.getOrderDate());
        values.put("orderTime", order.getOrderTime());
        values.put("size", order.getSize());
        values.put("quantity", order.getQuantity());
        values.put("basePrice", order.getBasePrice());
        values.put("finalPrice", order.getFinalPrice());
        long result = db.insert("Ordered", null, values);
        db.close();
        return result != -1;
    }



    public List<Pizza> getAllPizzas() {
        // Create a list to store the pizzas
        List<Pizza> pizzas = new ArrayList<>();

        // Create a SQLite database connection
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM Pizza";

        // Execute the query
        Cursor cursor = db.rawQuery(query, null);

        // Loop through the results
        while (cursor.moveToNext()) {
            // Extract the data
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String type = cursor.getString(cursor.getColumnIndexOrThrow("type"));
            String category = cursor.getString(cursor.getColumnIndexOrThrow("category"));
            int price = cursor.getInt(cursor.getColumnIndexOrThrow("price"));
            //int offer = cursor.getInt(cursor.getColumnIndexOrThrow("offer"));

            Pizza pizza = new Pizza(id, type, category, price, "", "");

            // Add the pizza to the list
            pizzas.add(pizza);
        }

        // Close the database connection
        cursor.close();
        db.close();

        return pizzas;
    }


    public boolean isPizzaOrderedBy(String pizzaType, String customerEmail) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Check if pizzaType is null or empty
        if (pizzaType == null || pizzaType.isEmpty()) {
            // Handle the case where pizzaType is not specified
            return false;
        }

        // Execute the SQL query with placeholders for pizzaType and customerEmail
        Cursor cursor = db.rawQuery("SELECT * FROM Ordered WHERE pizzaType = ? AND customerEmail = ?",
                new String[]{pizzaType, customerEmail});

        // Check if any rows were returned from the query
        boolean isOrdered = cursor.getCount() > 0;

        // Close the cursor and database connection
        cursor.close();
        db.close();

        return isOrdered;
    }


    public ArrayList<Ordered> getOrders(String customerEmail) {
        ArrayList<Ordered> orders = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Query to get all orders for a specific customer
        String query = "SELECT * FROM Ordered WHERE customerEmail = ?";
        Cursor cursor = db.rawQuery(query, new String[]{customerEmail});

        // Get the column indices once to avoid repeated calls
        int pizzaTypeIndex = cursor.getColumnIndex("pizzaType");
        int orderDateIndex = cursor.getColumnIndex("orderDate");
        int orderTimeIndex = cursor.getColumnIndex("orderTime");
        int sizeIndex = cursor.getColumnIndex("size");
        int quantityIndex = cursor.getColumnIndex("quantity");
        int basePriceIndex = cursor.getColumnIndex("basePrice");
        int finalPriceIndex = cursor.getColumnIndex("finalPrice");

        // Iterate through the result set and add orders to the list
        while (cursor.moveToNext()) {
            String pizzaType = cursor.getString(pizzaTypeIndex);
            String orderDate = cursor.getString(orderDateIndex);
            String orderTime = cursor.getString(orderTimeIndex);
            String size = cursor.getString(sizeIndex);
            int quantity = cursor.getInt(quantityIndex);
            double basePrice = cursor.getDouble(basePriceIndex);
            double finalPrice = cursor.getDouble(finalPriceIndex);

            // Create an Ordered object and add it to the orders list
            Ordered order = new Ordered(pizzaType, customerEmail, orderDate, orderTime, size, quantity,basePrice, finalPrice);
            orders.add(order);
        }

        // Close the cursor and database
        cursor.close();
        db.close();

        return orders;
    }

    public ArrayList<Pizza> getFavoritePizza(String customerEmail) {
        ArrayList<Pizza> favoritePizzas = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Query to get all reserved pizza for a specific customer
        String query = "SELECT * FROM Favorites F INNER JOIN Pizza P ON F.pizzaID = P.id WHERE F.customerEmail = ?";
        String[] selectionArgs = (customerEmail != null) ? new String[]{customerEmail} : null;

        Cursor cursor = db.rawQuery(query, selectionArgs);

        int pizzaIdIndex = cursor.getColumnIndex("id");
        int pizzaType = cursor.getColumnIndex("type");
        int pizzaCategory = cursor.getColumnIndex("category");
        int priceIndex = cursor.getColumnIndex("price");
        //int offerIndex = cursor.getColumnIndex("offer");



        while (cursor.moveToNext()) {
            int id = cursor.getInt(pizzaIdIndex);
            String type = cursor.getString(pizzaType);
            String category = cursor.getString(pizzaCategory);
            int price = cursor.getInt(priceIndex);
            //int offer = cursor.getInt(offerIndex);


            // Create a pizza object and add it to the order list
            Pizza pizza = new Pizza(id, type, category,  price,"", "");
            favoritePizzas.add(pizza);
        }

        // Close the cursor and database
        cursor.close();
        db.close();

        return favoritePizzas;
    }

    public boolean deleteOrder(String pizzaType, String customerEmail) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "pizzaType = ? AND customerEmail = ?";
        String[] whereArgs = {pizzaType, customerEmail};

        int deletedRows = db.delete("Ordered", query, whereArgs);
        db.close();

        return deletedRows > 0;
    }


    public boolean insertFavorites(Favorites favorites) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("pizzaID", favorites.getPizzaID());
        contentValues.put("customerEmail", favorites.getCustomerEmail());

        // If insertion is successful then result != -1
        long result = db.insert("Favorites", null, contentValues);
        db.close();
        return result != -1;
    }

    public boolean isPizzaInFavorites(int pizzaId, String customerEmail) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Favorites WHERE pizzaID = ? AND customerEmail = ?", new String[]{String.valueOf(pizzaId), customerEmail});

        boolean isFavorite = cursor.getCount() > 0;

        cursor.close();
        db.close();

        return isFavorite;
    }

    public boolean deleteFavorites(int pizzaId, String customerEmail) {
        SQLiteDatabase db = this.getWritableDatabase();
        int deletedRows = db.delete("Favorites", "pizzaID = ? AND customerEmail = ?", new String[]{String.valueOf(pizzaId), customerEmail});
        db.close();

        return deletedRows > 0;
    }


    public void printAllSpecialOffers() {
        SQLiteDatabase db = this.getReadableDatabase();

        // Query to get all special offers
        String query = "SELECT * FROM SpecialOffers";
        Cursor cursor = db.rawQuery(query, null);

        // Print header
        Log.d("SpecialOffers", "ID | Type | Size | Offer Period | Total Price");

        // Get column indices and ensure they are valid
        int idIndex = cursor.getColumnIndex("id");
        int typeIndex = cursor.getColumnIndex("type");
        int sizeIndex = cursor.getColumnIndex("size");
        int offerPeriodIndex = cursor.getColumnIndex("offer_period");
        int totalPriceIndex = cursor.getColumnIndex("total_price");

        if (idIndex == -1 || typeIndex == -1 || sizeIndex == -1 || offerPeriodIndex == -1 || totalPriceIndex == -1) {
            Log.e("SpecialOffers", "One or more columns not found in the database.");
            cursor.close();
            db.close();
            return;
        }

        // Iterate through the result set and print each offer
        while (cursor.moveToNext()) {
            int id = cursor.getInt(idIndex);
            String type = cursor.getString(typeIndex);
            String size = cursor.getString(sizeIndex);
            String offerPeriod = cursor.getString(offerPeriodIndex);
            double totalPrice = cursor.getDouble(totalPriceIndex);

            String offerDetails = id + " | " + type + " | " + size + " | " + offerPeriod + " | " + totalPrice;
            Log.d("SpecialOffers", offerDetails);
        }

        // Close the cursor and database
        cursor.close();
        db.close();
    }

    public List<SpecialOffer> getSpecialOffers() {
        List<SpecialOffer> specialOffers = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("SpecialOffers", null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String pizzaType = cursor.getString(cursor.getColumnIndex("type"));
                @SuppressLint("Range") String pizzaSize = cursor.getString(cursor.getColumnIndex("size"));
                @SuppressLint("Range")String offerPeriod = cursor.getString(cursor.getColumnIndex("offer_period"));
                @SuppressLint("Range")String startDateTime = cursor.getString(cursor.getColumnIndex("startDateTime"));
                @SuppressLint("Range")String endDateTime = cursor.getString(cursor.getColumnIndex("endDateTime"));
                @SuppressLint("Range") double totalPrice = cursor.getDouble(cursor.getColumnIndex("total_price"));

                SpecialOffer specialOffer = new SpecialOffer(0, pizzaType, pizzaSize, offerPeriod,startDateTime, endDateTime, totalPrice);
                specialOffers.add(specialOffer);
            } while (cursor.moveToNext());
            cursor.close();
        }

        db.close();
        return specialOffers;
    }

    public ArrayList<Ordered> getAllOrdersWithCustomerNames() {
        ArrayList<Ordered> orders = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT orders.*, customers.first_name || ' ' || customers.last_name AS customerName " +
                "FROM Ordered orders " +
                "JOIN Customer customers ON orders.customerEmail = customers.email";
        Cursor cursor = db.rawQuery(query, null);

        // Get column indices once
        int pizzaTypeIndex = cursor.getColumnIndex("pizzaType");
        int orderDateIndex = cursor.getColumnIndex("orderDate");
        int orderTimeIndex = cursor.getColumnIndex("orderTime");
        int sizeIndex = cursor.getColumnIndex("size");
        int quantityIndex = cursor.getColumnIndex("quantity");
        int basePriceIndex = cursor.getColumnIndex("basePrice");
        int finalPriceIndex = cursor.getColumnIndex("finalPrice");
        int customerNameIndex = cursor.getColumnIndex("customerName");

        while (cursor.moveToNext()) {
            String pizzaType = cursor.getString(pizzaTypeIndex);
            String orderDate = cursor.getString(orderDateIndex);
            String orderTime = cursor.getString(orderTimeIndex);
            String size = cursor.getString(sizeIndex);
            int quantity = cursor.getInt(quantityIndex);
            double basePrice = cursor.getDouble(basePriceIndex);
            double finalPrice = cursor.getDouble(finalPriceIndex);
            String customerName = cursor.getString(customerNameIndex);

            Ordered orderedPizza = new Ordered(pizzaType, orderDate, orderTime, size, quantity, basePrice, finalPrice, customerName);
            orders.add(orderedPizza);
        }

        cursor.close();
        db.close();
        return orders;
    }


    public Map<String, Integer> getOrdersCountByPizzaType() {
        Map<String, Integer> ordersCountMap = new HashMap<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT pizzaType, COUNT(*) AS orderCount " +
                "FROM Ordered " +
                "GROUP BY pizzaType";
        Cursor cursor = db.rawQuery(query, null);

        // Get column indices
        int pizzaTypeIndex = cursor.getColumnIndex("pizzaType");
        int orderCountIndex = cursor.getColumnIndex("orderCount");

        while (cursor.moveToNext()) {
            String pizzaType = cursor.getString(pizzaTypeIndex);
            int orderCount = cursor.getInt(orderCountIndex);
            ordersCountMap.put(pizzaType, orderCount);
        }

        cursor.close();
        db.close();

        return ordersCountMap;
    }

    public Map<String, Double> getTotalIncomeByPizzaType() {
        Map<String, Double> incomeMap = new HashMap<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT pizzaType, SUM(finalPrice) AS totalIncome FROM Ordered GROUP BY pizzaType";
        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            int pizzaTypeIndex = cursor.getColumnIndex("pizzaType");
            int totalIncomeIndex = cursor.getColumnIndex("totalIncome");

            if (pizzaTypeIndex >= 0 && totalIncomeIndex >= 0) {
                String pizzaType = cursor.getString(pizzaTypeIndex);
                double totalIncome = cursor.getDouble(totalIncomeIndex);
                incomeMap.put(pizzaType, totalIncome);
            }
        }

        cursor.close();
        db.close();

        return incomeMap;
    }


}