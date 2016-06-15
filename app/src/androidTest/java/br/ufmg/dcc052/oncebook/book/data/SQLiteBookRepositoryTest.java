package br.ufmg.dcc052.oncebook.book.data;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import java.util.List;

import br.ufmg.dcc052.oncebook.book.Book;
import br.ufmg.dcc052.oncebook.book.BookRepository;
import br.ufmg.dcc052.oncebook.book.SQLiteBookRepository;

/**
 * Created by xavier on 6/9/16.
 */
public class SQLiteBookRepositoryTest extends AndroidTestCase {

  private BookRepository bookRepository;

  private Book book1;
  private Book book2;

  @Override
  public void setUp() throws Exception {
    super.setUp();
    RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), "test_");
    bookRepository = new SQLiteBookRepository(context);

    book1 = new Book("Book1", "This is test book 1.", 1000);
    book2 = new Book("Book2", "This is test book 2.", 1000);
  }

  @Override
  public void tearDown() throws Exception {
    super.tearDown();
  }

  public void testInsertBook() {
    bookRepository.save(book1);

    Book testBook = bookRepository.getById(1);
    assertEquals(book1.getName(), testBook.getName());
    assertEquals(book1.getDescription(), testBook.getDescription());
  }

  public void testUpdateBook() {
    bookRepository.save(book1);
    book1.setDescription("Lorem ipsum dolor sit amet.");
    bookRepository.save(book1);

    Book testBook = bookRepository.getById(book1.getId());
    assertEquals(book1.getDescription(), testBook.getDescription());
  }

  public void testGetBookById() {
    bookRepository.save(book1);
    bookRepository.save(book2);

    Book testBook1 = bookRepository.getById(1);
    assertEquals(book1.getName(), testBook1.getName());
    assertEquals(book1.getDescription(), testBook1.getDescription());

    Book testBook2 = bookRepository.getById(2);
    assertEquals(book2.getName(), testBook2.getName());
    assertEquals(book2.getDescription(), testBook2.getDescription());
  }

  public void testGetAllBooks() {
    bookRepository.save(book1);
    bookRepository.save(book2);

    List<Book> allBooks = bookRepository.getAll();
    assertEquals(2, allBooks.size());
    assertEquals(book2.getName(), allBooks.get(0).getName());
    assertEquals(book1.getName(), allBooks.get(1).getName());
  }

  public void testFindBookByName() {
    bookRepository.save(book1);
    bookRepository.save(book2);

    List<Book> bookBooks = bookRepository.findByName("Book");
    assertEquals(2, bookBooks.size());
    assertEquals(book1.getName(), bookBooks.get(0).getName());
    assertEquals(book2.getName(), bookBooks.get(1).getName());

    List<Book> book1Books = bookRepository.findByName("Book1");
    assertEquals(1, book1Books.size());
    assertEquals(book1.getName(), book1Books.get(0).getName());
  }

  public void testDeleteBook() {
    bookRepository.save(book1);
    bookRepository.save(book2);

    bookRepository.delete(book1);

    List<Book> allBooks = bookRepository.getAll();
    assertEquals(1, allBooks.size());
    assertEquals(book2.getName(), allBooks.get(0).getName());
  }
}
