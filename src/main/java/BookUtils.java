import java.util.List;
public class BookUtils {
    private final BookRepository bookRepository;
    public BookUtils(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    public String getBookDetailsAsString(String title, String subtitle,
                                         List<String> authors, int year) {
        if (title == null || title.isBlank() || authors == null ||
                authors.isEmpty() || year <= 0) {

            throw new IllegalArgumentException("Invalid book details");
        }
        if (bookRepository.isAvailable(title)) {
            String authorString = String.join(", ", authors);
            return subtitle == null || subtitle.isBlank()
                    ? String.format("%s by %s (%d)", title, authorString, year)
                    : String.format("%s: %s by %s (%d)", title, subtitle,

                    authorString, year);
        }
        return "Book is unavailable!";
    }
    public interface BookRepository {
        boolean isAvailable(String title);
    }
}