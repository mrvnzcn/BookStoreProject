import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*; // for mock objects

class BookUtilsTest {
    private BookUtils.BookRepository mockRepository;
    private BookUtils bookUtils;

    @BeforeEach
    void setUp() {
        // Mocking BookRepository
        mockRepository = mock(BookUtils.BookRepository.class);
        // Injecting the mocked repository into the BookUtils class
        bookUtils = new BookUtils(mockRepository);
    }

    @Test
    void testGetBookDetailsAsString_ValidBookWithSubtitle() {
        // Arrange: Simulate that the book is available in the repository
        when(mockRepository.isAvailable("Test Title")).thenReturn(true);

        // Act: Call the method with valid inputs including a subtitle
        String result = bookUtils.getBookDetailsAsString(
                "Test Title", "A Subtitle", List.of("Author1", "Author2"), 2023);

        // Assert: Verify the formatted string is returned correctly
        assertEquals("Test Title: A Subtitle by Author1, Author2 (2023)", result);
    }

    @Test
    void testGetBookDetailsAsString_ValidBookWithoutSubtitle() {
        // Arrange: Simulate that the book is available in the repository
        when(mockRepository.isAvailable("Test Title")).thenReturn(true);

        // Act: Call the method with valid inputs but without a subtitle
        String result = bookUtils.getBookDetailsAsString(
                "Test Title", null, List.of("Author1", "Author2"), 2023);

        // Assert: Verify the formatted string is returned correctly when subtitle is absent
        assertEquals("Test Title by Author1, Author2 (2023)", result);
    }

    @Test
    void testGetBookDetailsAsString_BookUnavailable() {
        // Arrange: Simulate that the book is unavailable in the repository
        when(mockRepository.isAvailable("Test Title")).thenReturn(false);

        // Act: Call the method with valid inputs
        String result = bookUtils.getBookDetailsAsString(
                "Test Title", null, List.of("Author1", "Author2"), 2023);

        // Assert: Verify the method returns "Book is unavailable!"
        assertEquals("Book is unavailable!", result);
    }

    @Test
    void testGetBookDetailsAsString_InvalidDetails_ThrowsException() {
        // Act & Assert: Call the method with invalid inputs and ensure exceptions are thrown

        // Title is null
        assertThrows(IllegalArgumentException.class, () -> bookUtils.getBookDetailsAsString(
                null, null, List.of("Author1"), 2023));
        // Authors list is null
        assertThrows(IllegalArgumentException.class, () -> bookUtils.getBookDetailsAsString(
                "Valid Title", null, null, 2023));
        // Authors list is empty
        assertThrows(IllegalArgumentException.class, () -> bookUtils.getBookDetailsAsString(
                "Valid Title", null, List.of(), 2023));
        // Year is invalid
        assertThrows(IllegalArgumentException.class, () -> bookUtils.getBookDetailsAsString(
                "Valid Title", null, List.of("Author1"), -1));
    }

    @Test
    void testGetBookDetailsAsString_TitleOrSubtitleIsBlank() {
        // Act & Assert: Ensure the method handles blank title and subtitle appropriately

        // Title is blank
        assertThrows(IllegalArgumentException.class, () -> bookUtils.getBookDetailsAsString(
                "   ", "Subtitle", List.of("Author1"), 2023));

        // Subtitle is blank
        when(mockRepository.isAvailable("Test Title")).thenReturn(true);
        String result = bookUtils.getBookDetailsAsString(
                "Test Title", "   ", List.of("Author1"), 2023);

        // Assert: Verify the formatted string does not include a subtitle
        assertEquals("Test Title by Author1 (2023)", result);}

    @Test
    void testGetBookDetailsAsString_AuthorsContainNullOrEmpty() {
        // Arrange: Simulate that the book is available in the repository
        when(mockRepository.isAvailable("Test Title")).thenReturn(true);

        // Act & Assert: Handle edge cases in the authors list
        // Author list contains an empty string
        String result = bookUtils.getBookDetailsAsString(
                "Test Title", null, List.of("Author1", ""), 2023);
        assertEquals("Test Title by Author1,  (2023)", result);

        // Author list contains a null value
        assertThrows(NullPointerException.class, () -> bookUtils.getBookDetailsAsString(
                "Test Title", null, List.of("Author1", null), 2023));
    }

    @Test
    void testGetBookDetailsAsString_RepositoryThrowsException() {
        // Arrange: Simulate an exception in the repository
        when(mockRepository.isAvailable("Test Title")).thenThrow(new RuntimeException("Repository error"));

        // Act & Assert: Ensure the method throws the same exception
        assertThrows(RuntimeException.class, () -> bookUtils.getBookDetailsAsString(
                "Test Title", "Subtitle", List.of("Author1"), 2023));
    }
}
