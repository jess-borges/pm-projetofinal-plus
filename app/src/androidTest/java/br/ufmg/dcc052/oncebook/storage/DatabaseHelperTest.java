package br.ufmg.dcc052.oncebook.storage;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

/**
 * Created by xavier on 6/9/16.
 */
public class DatabaseHelperTest extends AndroidTestCase {

  private DatabaseHelper database;

  @Override
  public void setUp() throws Exception {
    super.setUp();
    RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), "test_");
    database = new DatabaseHelper(context);
  }

  @Override
  public void tearDown() throws Exception {
    database.close();
    super.tearDown();
  }

  public void testCreateDatabase() {
    String selectBooksTable = "SELECT name FROM sqlite_master WHERE type='table' AND name='books'";
    String selectCharactersTable = "SELECT name FROM sqlite_master WHERE type='table' AND name='characters'";
    String selectRelationshipsTable = "SELECT name FROM sqlite_master WHERE type='table' AND name='relationships'";

    assertTrue(database.getWritableDatabase().isOpen());
    assertEquals(1, database.getReadableDatabase().rawQuery(selectBooksTable, null).getCount());
    assertEquals(1, database.getReadableDatabase().rawQuery(selectCharactersTable, null).getCount());
    assertEquals(1, database.getReadableDatabase().rawQuery(selectRelationshipsTable, null).getCount());
  }
}
