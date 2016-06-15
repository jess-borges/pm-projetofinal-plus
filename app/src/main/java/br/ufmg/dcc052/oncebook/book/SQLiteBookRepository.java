package br.ufmg.dcc052.oncebook.book;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.ufmg.dcc052.oncebook.storage.DatabaseHelper;

/**
 * Created by xavier on 6/6/16.
 */
public class SQLiteBookRepository implements BookRepository {
/* New branch */
  public static final String TABLE_NAME = "books";
  public static final String COLUMN_NAME_ID = "_id";
  public static final String COLUMN_NAME_NAME = "name";
  public static final String COLUMN_NAME_DESCRIPTION = "description";
  public static final String COLUMN_NAME_NUMBERPAGES = "numpages";
  public static final String COLUMN_NAME_CURRENTPAGE = "curentpage";
  public static final String[] ALL_COLUMNS = { COLUMN_NAME_ID, COLUMN_NAME_NAME,
    COLUMN_NAME_DESCRIPTION, COLUMN_NAME_NUMBERPAGES, COLUMN_NAME_CURRENTPAGE };

  private DatabaseHelper databaseHelper;

  public SQLiteBookRepository(Context context) {
    this.databaseHelper = new DatabaseHelper(context);
  }

  @Override
  public Book getById(Integer id) {
    Book book;
    SQLiteDatabase db = databaseHelper.getReadableDatabase();
    String where = COLUMN_NAME_ID + "=" + id;
    Cursor cursor = db.query(TABLE_NAME, ALL_COLUMNS, where, null, null, null, null);
    if (cursor != null) {
      cursor.moveToFirst();
    }
    book = cursorToEntity(cursor);
    cursor.close();
    return book;
  }

  @Override
  public List<Book> getAll() {
    List<Book> books;
    SQLiteDatabase db = databaseHelper.getReadableDatabase();
    Cursor cursor = db.query(TABLE_NAME, ALL_COLUMNS, null, null, null, null, "_id DESC");
    try {
      books = cursorToEntities(cursor);
    } finally {
      cursor.close();
    }
    return books;
  }

  @Override
  public List<Book> findByName(String name) {
    List<Book> books;
    SQLiteDatabase db = databaseHelper.getReadableDatabase();
    String where = COLUMN_NAME_NAME + " LIKE '" + name + "%'";
    Cursor cursor = db.query(TABLE_NAME, ALL_COLUMNS, where, null, null, null, null);
    books = cursorToEntities(cursor);
    cursor.close();
    return books;
  }

  @Override
  public void save(Book book) {
    SQLiteDatabase db = databaseHelper.getWritableDatabase();

    ContentValues values = new ContentValues();
    values.put(COLUMN_NAME_NAME, book.getName());
    values.put(COLUMN_NAME_DESCRIPTION, book.getDescription());
    values.put(COLUMN_NAME_NUMBERPAGES, book.getNumberOfPages());
    values.put(COLUMN_NAME_CURRENTPAGE, book.getCurrentPage());

    String where = COLUMN_NAME_ID + "=" + book.getId();

    if (book.getId() != 0) {
      if (db.update(TABLE_NAME, values, where, null) == 0) {
        book.setId((int) db.insert(TABLE_NAME, null, values));
      }
    } else {
      book.setId((int) db.insert(TABLE_NAME, null, values));
    }
  }

  @Override
  public void delete(Book book) {
    SQLiteDatabase db = databaseHelper.getWritableDatabase();
    String where = COLUMN_NAME_ID + "=" + book.getId();
    db.delete(TABLE_NAME, where, null);
  }

  private Book cursorToEntity(Cursor cursor) {
    if(cursor == null || cursor.getCount() == 0) {
      return null;
    }
    int id = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_ID));
    String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_NAME));
    String description = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_DESCRIPTION));
    int numberOfPages = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_NUMBERPAGES));
    int currentPage = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_CURRENTPAGE));
    return new Book(id, name, description, numberOfPages, currentPage);
  }

  private List<Book> cursorToEntities(Cursor cursor) {
    if(cursor == null) {
      return null;
    }
    List<Book> books = new ArrayList<>();
    cursor.moveToFirst();
    while(!cursor.isAfterLast()) {
      books.add(this.cursorToEntity(cursor));
      cursor.moveToNext();
    }
    return books;
  }
}
