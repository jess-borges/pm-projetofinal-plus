package br.ufmg.dcc052.oncebook.storage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import br.ufmg.dcc052.oncebook.book.SQLiteBookRepository;
import br.ufmg.dcc052.oncebook.character.SQLiteCharacterRepository;
import br.ufmg.dcc052.oncebook.relationship.SQLiteRelationshipRepository;

/**
 * Created by xavier on 6/6/16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

  private static final String DATABASE_NAME = "oncebook.db";
  private static final int DATABASE_VERSION = 1;

  public DatabaseHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL(createBooksTable());
    db.execSQL(createCharactersTable());
    db.execSQL(createRelationshipsTable());
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  private String createBooksTable() {
    return "CREATE TABLE IF NOT EXISTS " + SQLiteBookRepository.TABLE_NAME + " (" +
      SQLiteBookRepository.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
      SQLiteBookRepository.COLUMN_NAME_NAME + " TEXT, " +
      SQLiteBookRepository.COLUMN_NAME_DESCRIPTION + " TEXT," +
      SQLiteBookRepository.COLUMN_NAME_NUMBERPAGES + " INTEGER," +
      SQLiteBookRepository.COLUMN_NAME_CURRENTPAGE + " INTEGER" +
      ")";
  }

  private String createCharactersTable() {
    return "CREATE TABLE IF NOT EXISTS " + SQLiteCharacterRepository.TABLE_NAME + " (" +
      SQLiteCharacterRepository.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
      SQLiteCharacterRepository.COLUMN_NAME_NAME + " TEXT, " +
      SQLiteCharacterRepository.COLUMN_NAME_DESCRIPTION + " TEXT, " +
      SQLiteCharacterRepository.COLUMN_NAME_BOOK + " INTEGER, " +
      SQLiteCharacterRepository.COLUMN_NAME_APPEARANCEPAGE + " INTEGER, " +
      SQLiteCharacterRepository.COLUMN_NAME_PICTURE + " BLOB, " +
      "FOREIGN KEY (" + SQLiteCharacterRepository.COLUMN_NAME_BOOK + ") REFERENCES " +
        SQLiteBookRepository.TABLE_NAME + "(" + SQLiteBookRepository.COLUMN_NAME_ID +
      "));";
  }

  private String createRelationshipsTable() {
    return "CREATE TABLE IF NOT EXISTS " + SQLiteRelationshipRepository.TABLE_NAME + " (" +
      SQLiteRelationshipRepository.COLUMN_NAME_FISRTCHARACTER + " INTEGER, " +
      SQLiteRelationshipRepository.COLUMN_NAME_SECONDCHARACTER + " INTEGER, " +
      SQLiteRelationshipRepository.COLUMN_NAME_NAME + " TEXT, " +
      "PRIMARY KEY (" + SQLiteRelationshipRepository.COLUMN_NAME_FISRTCHARACTER + ", " +
        SQLiteRelationshipRepository.COLUMN_NAME_SECONDCHARACTER + "), " +
      "FOREIGN KEY (" + SQLiteRelationshipRepository.COLUMN_NAME_FISRTCHARACTER + ") REFERENCES " +
        SQLiteCharacterRepository.TABLE_NAME + "(" + SQLiteCharacterRepository.COLUMN_NAME_ID + "), " +
      "FOREIGN KEY (" + SQLiteRelationshipRepository.COLUMN_NAME_SECONDCHARACTER + ") REFERENCES " +
        SQLiteCharacterRepository.TABLE_NAME + "(" + SQLiteCharacterRepository.COLUMN_NAME_ID + ")" +
      ");";
  }
}
