package br.ufmg.dcc052.oncebook.book.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.ufmg.dcc052.oncebook.OncebookConstants;
import br.ufmg.dcc052.oncebook.R;
import br.ufmg.dcc052.oncebook.book.Book;
import br.ufmg.dcc052.oncebook.book.BookPresenter;
import br.ufmg.dcc052.oncebook.book.BookView;
import br.ufmg.dcc052.oncebook.character.ui.CharacterActivity;

public class BookActivity
  extends AppCompatActivity
  implements BookView,
             View.OnClickListener,
             AdapterView.OnItemLongClickListener,
             AdapterView.OnItemClickListener {

  private BookAdapter adapter;
  private BookPresenter presenter;

  private ListView listView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_books);

    presenter = new BookPresenter(this, this);

    listView = (ListView) findViewById(R.id.listViewRecords);
    listView.setOnItemLongClickListener(this);
    listView.setOnItemClickListener(this);
    presenter.refresh();

    FloatingActionButton buttonCreateBook = (FloatingActionButton) findViewById(R.id.buttonCreateBook);
    buttonCreateBook.setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    presenter.createBook();
  }

  @Override
  public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
    Book book = (Book)parent.getItemAtPosition(position);
    presenter.selectBook(book);
    return true;
  }

  @Override
  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    Book book = (Book)parent.getItemAtPosition(position);
    Intent intent = new Intent(this, CharacterActivity.class);
    intent.putExtra(OncebookConstants.EXTRA_BOOK_ID, book.getId());
    this.startActivity(intent);
  }

  @Override
  public void showSaveBookDialog() {
    new SaveBookDialog(presenter, this).show();
  }

  @Override
  public void showBookSelectedDialog() {
    new BookSelectedDialog(presenter, this).show();
  }

  @Override
  public void showCurrentPageDialog() { new CurrentPageDialog(presenter, this).show(); }

  @Override
  public void showBookDeletedToast() {
    Toast
      .makeText(this, "Book deleted successfully", Toast.LENGTH_SHORT)
      .show();
  }

  @Override
  public void onItemsNext(List<Book> books) {
    if (adapter == null) {
      adapter = new BookAdapter(this, books);
    } else {
      adapter.clear();
      adapter.addAll(books);
      adapter.notifyDataSetChanged();
    }
    listView.setAdapter(adapter);
  }

  @Override
  public void onItemsError(Throwable error) {
    adapter.notifyDataSetInvalidated();
    Toast
      .makeText(getApplicationContext(), "An error occurred while fetching books information", Toast.LENGTH_SHORT)
      .show();
  }
}
