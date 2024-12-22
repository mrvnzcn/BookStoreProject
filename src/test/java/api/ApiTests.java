package api;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.testng.Assert;
import java.util.List;
import java.util.Map;


public class ApiTests {
    @Test public void testFetchBookDetails() {
        String baseUrl = "https://openlibrary.org/api/books";
        String isbn = "9780261103573";

        // send get request
        Response response = RestAssured.given()
                .queryParam("bibkeys","ISBN:" + isbn)
                .queryParam("format", "json")
                .queryParam("jscmd", "data")
                .get(baseUrl);

        // extract book details
        Map<String, Object> jsonData = response.jsonPath().getMap("$");
        String bookTitle = ((Map<String, Object>) jsonData.get("ISBN:"+isbn)).get("title").toString();
        String bookSubtitle = ((Map<String, Object>) jsonData.get("ISBN:"+isbn)).get("subtitle").toString();
        List<Map<String, Object>> authorsList = (List<Map<String, Object>>) ((Map<String, Object>) jsonData.get("ISBN:9780261103573")).get("authors");
        String authorName = authorsList.getFirst().get("name").toString();

        // assert status code
        Assert.assertEquals((response.getStatusCode()), 200, "Expected status code is 200");
        // assert key fields
        Assert.assertEquals(bookTitle,"The Fellowship of the Ring","Expected book title is \"The Fellowship of the Ring\"");
        Assert.assertEquals(bookSubtitle, "The Lord of the Rings, Part I", "Expected book subtitle is \"BThe Lord of the Rings, Part I\"");
        Assert.assertEquals(authorName, "J.R.R. Tolkien", "Expected author name is \"J.R.R. Tolkien\"");
    }
}
