# BookUtils & API Testing Project

This project is about formatting book details and getting data from the Open Library API. It includes tests written with JUnit 5, Mockito, and RestAssured.

---

## 📂 Project Structure

### 1. **BookUtils**
- A helper class to format book details like title, subtitle, authors, and year.
- Uses a `BookRepository` interface to check if the book is available.

### 2. **Tests**
- **Unit Tests:** Tests the `BookUtils` class and edge cases (JUnit 5 and Mockito).
- **API Test:** Tests the Open Library API and checks book details using ISBN (RestAssured).

---

## 🛠️ Used Technologies
- **Java 11+**
- **JUnit 5:** For unit tests.
- **Mockito:** For creating mock objects.
- **RestAssured:** For API testing.
