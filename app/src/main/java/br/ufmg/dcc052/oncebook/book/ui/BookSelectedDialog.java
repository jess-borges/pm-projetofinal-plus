package br.ufmg.dcc052.oncebook.book.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

import br.ufmg.dcc052.oncebook.Showable;
import br.ufmg.dcc052.oncebook.book.BookPresenter;

/**
 * Created by xavier on 6/13/16.
 */
public class BookSelectedDialog implements Showable, DialogInterface.OnClickListener  {

  private BookPresenter presenter;

  private Dialog dialog;


  public BookSelectedDialog(BookPresenter presenter, Context context) {
    this.presenter = presenter;

    final CharSequence[] options = {"Edit", "Edit current page", "Delete"};
    dialog = new AlertDialog.Builder(context)
      .setTitle(presenter.getSelectedBook().getName())
      .setItems(options, this)
      .create();
  }

  @Override
  public void show() {
    this.dialog.show();
  }

  @Override
  public void onClick(DialogInterface dialog, int which) {
    switch (which) {
      case 0:
        presenter.editBook(presenter.getSelectedBook());
        break;
      case 1:
        presenter.editCurrentPage(presenter.getSelectedBook());
        break;
      case 2:
        presenter.deleteBook(presenter.getSelectedBook());
        break;
    }
    dialog.dismiss();
  }
}
