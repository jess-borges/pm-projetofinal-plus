package br.ufmg.dcc052.oncebook.book.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import br.ufmg.dcc052.oncebook.R;
import br.ufmg.dcc052.oncebook.Showable;
import br.ufmg.dcc052.oncebook.book.Book;
import br.ufmg.dcc052.oncebook.book.BookPresenter;

/**
 * Created by Jess on 15/06/2016.
 */
public class CurrentPageDialog implements Showable, DialogInterface.OnClickListener {

private BookPresenter presenter;

private Dialog dialog;
private EditText etBookCurrentPage;
private TextView tvBookCurrentPage;

  public CurrentPageDialog(BookPresenter presenter, Context context) {
    this.presenter = presenter;
    Book book = this.presenter.getSelectedBook();

    View formCurrentPage = LayoutInflater.from(context).inflate(R.layout.form_currentpage_book, null, false);
    etBookCurrentPage = (EditText) formCurrentPage.findViewById(R.id.etBookCurrentPage);
    tvBookCurrentPage = (TextView) formCurrentPage.findViewById(R.id.tvBookCurrentPage);
    tvBookCurrentPage.setText("Current page: " + String.format("%d", book.getCurrentPage()));


    dialog = new AlertDialog.Builder(context)
      .setTitle(book.getName())
      .setView(formCurrentPage)
      .setPositiveButton("Save", this)
      .create();
  }
  @Override
  public void show() {
    dialog.show();
  }

  @Override
  public void onClick(DialogInterface dialog, int which) {
    Book book = presenter.getSelectedBook();

    try {
      book.setCurrentPage(Integer.parseInt(etBookCurrentPage.getText().toString()));
      presenter.saveBook(book);
    }catch(Exception e){
      /* If user does not enter a current page, format is incorrect, so do nothing */
    }

    dialog.dismiss();
  }
}
