package br.ufmg.dcc052.oncebook.character.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

import br.ufmg.dcc052.oncebook.Showable;
import br.ufmg.dcc052.oncebook.character.CharacterPresenter;

/**
 * Created by xavier on 6/13/16.
 */
public class CharacterSelectedDialog implements Showable, DialogInterface.OnClickListener  {

  private CharacterPresenter presenter;

  private Dialog dialog;

  public CharacterSelectedDialog(CharacterPresenter presenter, Context context) {
    this.presenter = presenter;

    final CharSequence[] options = {"Edit", "Delete"};
    dialog = new AlertDialog.Builder(context)
      .setTitle(presenter.getSelectedCharacter().getName())
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
        presenter.editCharacter(presenter.getSelectedCharacter());
        break;
      case 1:
        presenter.deleteCharacter(presenter.getSelectedCharacter());
        break;
    }
    dialog.dismiss();
  }
}
