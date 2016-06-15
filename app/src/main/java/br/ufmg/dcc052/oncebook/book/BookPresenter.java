package br.ufmg.dcc052.oncebook.book;

import android.content.Context;

import br.ufmg.dcc052.oncebook.Refreshable;

/**
 * Created by xavier on 6/14/16.
 */
public class BookPresenter implements Refreshable {

  private Book selectedBook;

  private BookView view;
  private BookRepository bookRepository;

  public BookPresenter(Context context, BookView view) {
    this.bookRepository = new SQLiteBookRepository(context);
    this.view = view;
  }

  public void saveBook(Book book) {
    setSelectedBook(null);
    bookRepository.save(book);
    refresh();
  }

  public void createBook() {
    setSelectedBook(null);
    view.showSaveBookDialog();
  }

  public void selectBook(Book book) {
    setSelectedBook(book);
    view.showBookSelectedDialog();
  }

  public void editBook(Book book) {
    setSelectedBook(book);
    view.showSaveBookDialog();
  }

  public void editCurrentPage(Book book) {
    setSelectedBook(book);
    view.showCurrentPageDialog();
  }
  public void deleteBook(Book book) {
    setSelectedBook(null);
    bookRepository.delete(book);
    view.showBookDeletedToast();
    refresh();
  }

  @Override
  public void refresh() {
    view.onItemsNext(bookRepository.getAll());
  }

  public Book getSelectedBook() {
    return selectedBook;
  }
  public void setSelectedBook(Book selectedBook) {
    this.selectedBook = selectedBook;
  }
}
