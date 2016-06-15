package br.ufmg.dcc052.oncebook.book.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import br.ufmg.dcc052.oncebook.R;
import br.ufmg.dcc052.oncebook.Showable;
import br.ufmg.dcc052.oncebook.book.Book;
import br.ufmg.dcc052.oncebook.book.BookPresenter;

/**
 * Created by xavier on 6/13/16.
 */
public class SaveBookDialog implements Showable, DialogInterface.OnClickListener {

  private BookPresenter presenter;

  private Dialog dialog;
  private EditText etBookName;
  private EditText etBookDescription;
  private EditText etBookNumPages;

  public SaveBookDialog(BookPresenter presenter, Context context) {
    this.presenter = presenter;

    View formBookView = LayoutInflater.from(context).inflate(R.layout.form_book, null, false);
    etBookName = (EditText) formBookView.findViewById(R.id.etBookName);
    etBookDescription = (EditText) formBookView.findViewById(R.id.etBookDescription);
    etBookNumPages = (EditText) formBookView.findViewById(R.id.etBookNumPages);

    Book book = presenter.getSelectedBook();

    if (book != null) {
      etBookName.setText(book.getName());
      etBookDescription.setText(book.getDescription());
      etBookNumPages.setText(Integer.toString(book.getNumberOfPages()));
    }

    dialog = new AlertDialog.Builder(context)
      .setTitle(book == null ? "Create book" : "Edit book")
      .setView(formBookView)
      .setPositiveButton(book == null ? "Create" : "Save", this)
      .create();
  }

  @Override
  public void show() {
    dialog.show();
  }

  @Override
  public void onClick(DialogInterface dialog, int which) {
    Book book = presenter.getSelectedBook();

    if(book == null) {
      book = new Book(etBookName.getText().toString(), etBookDescription.getText().toString(), Integer.parseInt(etBookNumPages.getText().toString()));
    } else {
      book.setName(etBookName.getText().toString());
      book.setDescription(etBookDescription.getText().toString());
      book.setNumberOfPages(Integer.parseInt(etBookNumPages.getText().toString()));
    }

    presenter.saveBook(book);
    dialog.dismiss();
  }
}
