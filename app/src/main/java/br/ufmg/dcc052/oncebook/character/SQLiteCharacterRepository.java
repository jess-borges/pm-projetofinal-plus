package br.ufmg.dcc052.oncebook.character;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

import br.ufmg.dcc052.oncebook.book.Book;
import br.ufmg.dcc052.oncebook.book.BookRepository;
import br.ufmg.dcc052.oncebook.book.SQLiteBookRepository;
import br.ufmg.dcc052.oncebook.BitmapUtils;
import br.ufmg.dcc052.oncebook.storage.DatabaseHelper;

/**
 * Created by xavier on 6/6/16.
 */
public class SQLiteCharacterRepository implements CharacterRepository {

  public static final String TABLE_NAME = "characters";
  public static final String COLUMN_NAME_ID = "_id";
  public static final String COLUMN_NAME_NAME = "name";
  public static final String COLUMN_NAME_DESCRIPTION = "description";
  public static final String COLUMN_NAME_BOOK = "book_id";
  public static final String COLUMN_NAME_APPEARANCEPAGE = "appearancePage";
  public static final String COLUMN_NAME_PICTURE = "picture";

  private static final String[] ALL_COLUMNS = { COLUMN_NAME_ID, COLUMN_NAME_NAME,
    COLUMN_NAME_DESCRIPTION, COLUMN_NAME_BOOK, COLUMN_NAME_APPEARANCEPAGE, COLUMN_NAME_PICTURE };
  private static final String[] ALL_COLUMNS_BUT_BOOK = { COLUMN_NAME_ID, COLUMN_NAME_NAME,
    COLUMN_NAME_DESCRIPTION, COLUMN_NAME_APPEARANCEPAGE, COLUMN_NAME_PICTURE };

  private DatabaseHelper databaseHelper;
  private BookRepository bookRepository;

  public SQLiteCharacterRepository(Context context) {
    this.databaseHelper = new DatabaseHelper(context);
    this.bookRepository = new SQLiteBookRepository(context);
  }

  @Override
  public Character getById(Integer id) {
    Character character;
    SQLiteDatabase db = databaseHelper.getReadableDatabase();
    String where = COLUMN_NAME_ID + "=" + id;
    Cursor cursor = db.query(TABLE_NAME, ALL_COLUMNS, where, null, null, null, null);
    if (cursor != null) {
      cursor.moveToFirst();
    }
    character = cursorToEntity(cursor);
    cursor.close();
    return character;
  }

  @Override
  public Character getByName(String name) {
    Character character;
    SQLiteDatabase db = databaseHelper.getReadableDatabase();
    String where = COLUMN_NAME_NAME + "='" + name + "'";
    Cursor cursor = db.query(TABLE_NAME, ALL_COLUMNS, where, null, null, null, null);
    if (cursor != null) {
      cursor.moveToFirst();
    }
    character = cursorToEntity(cursor);
    cursor.close();
    return character;
  }

  @Override
  public List<Character> getAll() {
    List<Character> characters;
    SQLiteDatabase db = databaseHelper.getReadableDatabase();
    Cursor cursor = db.query(TABLE_NAME, ALL_COLUMNS, null, null, null, null, null);
    try {
      characters = cursorToEntities(cursor, null);
    } finally {
      cursor.close();
    }
    return characters;
  }

  @Override
  public List<Character> getAllByBook(Book book) {
    List<Character> characters;
    SQLiteDatabase db = databaseHelper.getReadableDatabase();
    String where = COLUMN_NAME_BOOK + "=" + book.getId();
    Cursor cursor = db.query(TABLE_NAME, ALL_COLUMNS_BUT_BOOK, where, null, null, null, null);
    try {
      characters = cursorToEntities(cursor, book);
    } finally {
      cursor.close();
    }
    return characters;
  }

  @Override
  public List<Character> getAllFromSameBook(Character character) {
    List<Character> characters;
    SQLiteDatabase db = databaseHelper.getReadableDatabase();
    String where = COLUMN_NAME_BOOK + "=" + character.getBook().getId() +
      " AND " + COLUMN_NAME_ID + "!=" + character.getId();
    Cursor cursor = db.query(TABLE_NAME, ALL_COLUMNS_BUT_BOOK, where, null, null, null, null);
    try {
      characters = cursorToEntities(cursor, character.getBook());
    } finally {
      cursor.close();
    }
    return characters;
  }

  @Override
  public List<Character> findByName(String name) {
    List<Character> characters;
    SQLiteDatabase db = databaseHelper.getReadableDatabase();
    String where = COLUMN_NAME_NAME + " LIKE '" + name + "%'";
    Cursor cursor = db.query(TABLE_NAME, ALL_COLUMNS, where, null, null, null, null);
    characters = cursorToEntities(cursor, null);
    cursor.close();
    return characters;
  }

  @Override
  public void save(Character character) {
    SQLiteDatabase db = databaseHelper.getWritableDatabase();

    ContentValues values = new ContentValues();
    values.put(COLUMN_NAME_NAME, character.getName());
    values.put(COLUMN_NAME_DESCRIPTION, character.getDescription());
    values.put(COLUMN_NAME_APPEARANCEPAGE, character.getAppearancePage());
    values.put(COLUMN_NAME_PICTURE, BitmapUtils.getBytes(character.getPicture()));
    values.put(COLUMN_NAME_BOOK, character.getBook().getId());

    if (character.getId() != 0) {
      String where = COLUMN_NAME_ID + "=" + character.getId();
      if (db.update(TABLE_NAME, values, where, null) == 0) {
        character.setId((int) db.insert(TABLE_NAME, null, values));
      }
    } else {
      character.setId((int) db.insert(TABLE_NAME, null, values));
    }
  }

  @Override
  public void delete(Character character) {
    SQLiteDatabase db = databaseHelper.getWritableDatabase();
    String where = COLUMN_NAME_ID + "=" + character.getId();
    db.delete(TABLE_NAME, where, null);
  }

  private Character cursorToEntity(Cursor cursor) {
    if(cursor == null || cursor.getCount() == 0) {
      return null;
    }

    int id = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_ID));
    String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_NAME));
    String description = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_DESCRIPTION));
    int appearancePage = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_APPEARANCEPAGE));
    Bitmap picture = BitmapUtils.getBitmap(cursor.getBlob(cursor.getColumnIndex(COLUMN_NAME_PICTURE)));

    // has book_id column ==> return character with book
    try {
      int bookId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NAME_BOOK));
      Book book = this.bookRepository.getById(bookId);
      return new Character(id, name, description, book, appearancePage, picture);
    }
    // else ==> return character without book
    catch(IllegalArgumentException e) {
      return new Character(id, name, description, null, appearancePage, picture);
    }
  }

  private List<Character> cursorToEntities(Cursor cursor, Book book) {
    if(cursor == null) {
      return null;
    }
    List<Character> characters = new ArrayList<>();
    cursor.moveToFirst();
    while(!cursor.isAfterLast()) {
      Character character = this.cursorToEntity(cursor);
      if (book != null) {
        character.setBook(book);
      }
      characters.add(character);
      cursor.moveToNext();
    }
    return characters;
  }
}
