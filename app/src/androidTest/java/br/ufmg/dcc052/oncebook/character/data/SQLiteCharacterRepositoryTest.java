package br.ufmg.dcc052.oncebook.character.data;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import java.util.List;

import br.ufmg.dcc052.oncebook.book.Book;
import br.ufmg.dcc052.oncebook.book.BookRepository;
import br.ufmg.dcc052.oncebook.book.SQLiteBookRepository;
import br.ufmg.dcc052.oncebook.character.CharacterRepository;
import br.ufmg.dcc052.oncebook.character.SQLiteCharacterRepository;
import br.ufmg.dcc052.oncebook.character.Character;

/**
 * Created by xavier on 6/9/16.
 */
public class SQLiteCharacterRepositoryTest extends AndroidTestCase {

  private BookRepository bookRepository;
  private CharacterRepository characterRepository;

  private Book book1;
  private Book book2;

  private Character character1;
  private Character character2;
  private Character character3;

  @Override
  public void setUp() throws Exception {
    super.setUp();
    RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), "test_");
    bookRepository = new SQLiteBookRepository(context);
    characterRepository = new SQLiteCharacterRepository(context);

    book1 = new Book("Book1", "This is test book 1.", 1000);
    book2 = new Book("Book2", "This is test book 2.", 1000);

    character1 = new Character("Character1", "This is test character 1.", book1);
    character2 = new Character("Character2", "This is test character 2.", book1);
    character3 = new Character("Character3", "This is test character 3.", book2);
  }

  @Override
  public void tearDown() throws Exception {
    super.tearDown();
  }

  public void testInsertCharacter() {
    characterRepository.save(character1);

    Character testCharacter = characterRepository.getById(1);
    assertEquals(character1.getName(), testCharacter.getName());
    assertEquals(character1.getDescription(), testCharacter.getDescription());
  }

  public void testUpdateCharacter() {
    characterRepository.save(character1);
    character1.setDescription("Lorem ipsum dolor sit amet.");
    characterRepository.save(character1);

    Character testCharacter = characterRepository.getById(character1.getId());
    assertEquals(character1.getDescription(), testCharacter.getDescription());
  }

  public void testGetCharacterById() {
    characterRepository.save(character1);
    characterRepository.save(character2);

    Character testCharacter1 = characterRepository.getById(1);
    assertEquals(character1.getName(), testCharacter1.getName());
    assertEquals(character1.getDescription(), testCharacter1.getDescription());

    Character testCharacter2 = characterRepository.getById(2);
    assertEquals(character2.getName(), testCharacter2.getName());
    assertEquals(character2.getDescription(), testCharacter2.getDescription());
  }

  public void testGetCharacterByName() {
    characterRepository.save(character1);
    characterRepository.save(character2);

    Character testCharacter1 = characterRepository.getByName("Character1");
    assertEquals(character1.getDescription(), testCharacter1.getDescription());

    Character testCharacter2 = characterRepository.getByName("Character2");
    assertEquals(character2.getDescription(), testCharacter2.getDescription());
  }

  public void testGetAllCharacters() {
    characterRepository.save(character1);
    characterRepository.save(character2);

    List<Character> allCharacters = characterRepository.getAll();
    assertEquals(2, allCharacters.size());
    assertEquals(character1.getName(), allCharacters.get(0).getName());
    assertEquals(character2.getName(), allCharacters.get(1).getName());
  }

  public void testGetAllCharactersByBook() {
    bookRepository.save(book1);
    bookRepository.save(book2);
    characterRepository.save(character1);
    characterRepository.save(character2);
    characterRepository.save(character3);

    List<Character> book1Characters = characterRepository.getAllByBook(book1);
    assertEquals(2, book1Characters.size());
    assertEquals(character1.getName(), book1Characters.get(0).getName());
    assertEquals(character2.getName(), book1Characters.get(1).getName());

    List<Character> book2Characters = characterRepository.getAllByBook(book2);
    assertEquals(1, book2Characters.size());
    assertEquals(character3.getName(), book2Characters.get(0).getName());
  }

  public void testGetAllCharactersFromSameBook() {
    bookRepository.save(book1);
    bookRepository.save(book2);
    characterRepository.save(character1);
    characterRepository.save(character2);
    characterRepository.save(character3);

    List<Character> character1BookCharacters = characterRepository.getAllFromSameBook(character1);
    assertEquals(1, character1BookCharacters.size());
    assertEquals(character2.getName(), character1BookCharacters.get(0).getName());

    List<Character> character2BookCharacters = characterRepository.getAllFromSameBook(character2);
    assertEquals(1, character2BookCharacters.size());
    assertEquals(character1.getName(), character2BookCharacters.get(0).getName());

    List<Character> character3BookCharacters = characterRepository.getAllFromSameBook(character3);
    assertEquals(0, character3BookCharacters.size());
  }

  public void testFindCharacterByName() {
    characterRepository.save(character1);
    characterRepository.save(character2);

    List<Character> characterCharacters = characterRepository.findByName("Character");
    assertEquals(2, characterCharacters.size());
    assertEquals(character1.getName(), characterCharacters.get(0).getName());
    assertEquals(character2.getName(), characterCharacters.get(1).getName());

    List<Character> character1Characters = characterRepository.findByName("Character1");
    assertEquals(1, character1Characters.size());
    assertEquals(character1.getName(), character1Characters.get(0).getName());
  }

  public void testDeleteCharacter() {
    characterRepository.save(character1);
    characterRepository.save(character2);

    characterRepository.delete(character1);

    List<Character> allBooks = characterRepository.getAll();
    assertEquals(1, allBooks.size());
    assertEquals(character2.getName(), allBooks.get(0).getName());
  }
}
