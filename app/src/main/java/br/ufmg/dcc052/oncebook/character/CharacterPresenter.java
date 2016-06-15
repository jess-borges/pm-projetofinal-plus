package br.ufmg.dcc052.oncebook.character;

import android.content.Context;

import br.ufmg.dcc052.oncebook.Refreshable;
import br.ufmg.dcc052.oncebook.book.Book;
import br.ufmg.dcc052.oncebook.book.BookRepository;
import br.ufmg.dcc052.oncebook.book.SQLiteBookRepository;

/**
 * Created by xavier on 6/14/16.
 */
public class CharacterPresenter implements Refreshable {

  private Character selectedCharacter;
  private Book book;

  private CharacterView view;
  private CharacterRepository characterRepository;
  private BookRepository bookRepository;

  public CharacterPresenter(Context context, CharacterView view, int bookId) {
    this.view = view;
    this.characterRepository = new SQLiteCharacterRepository(context);
    this.bookRepository = new SQLiteBookRepository(context);
    this.book = bookRepository.getById(bookId);
  }

  public void saveCharacter(Character character) {
    setSelectedCharacter(null);
    characterRepository.save(character);
    refresh();
  }

  public void createCharacter() {
    setSelectedCharacter(null);
    view.showSaveCharacterDialog();
  }

  public void selectCharacter(Character character) {
    setSelectedCharacter(character);
    view.showCharacterSelectedDialog();
  }

  public void editCharacter(Character character) {
    setSelectedCharacter(character);
    view.showSaveCharacterDialog();
  }

  public void deleteCharacter(Character character) {
    setSelectedCharacter(null);
    characterRepository.delete(character);
    view.showCharacterDeletedToast();
    refresh();
  }

  @Override
  public void refresh() {
    view.onItemsNext(characterRepository.getAllByBook(book));
  }

  public Character getSelectedCharacter() {
    return selectedCharacter;
  }
  public void setSelectedCharacter(Character selectedCharacter) {
    this.selectedCharacter = selectedCharacter;
  }

  public Book getBook() {
    return book;
  }
  public void setBook(Book book) {
    this.book = book;
    refresh();
  }
}
