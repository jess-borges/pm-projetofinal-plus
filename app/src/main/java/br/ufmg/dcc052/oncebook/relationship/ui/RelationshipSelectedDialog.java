package br.ufmg.dcc052.oncebook.relationship.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

import br.ufmg.dcc052.oncebook.Showable;
import br.ufmg.dcc052.oncebook.relationship.RelationshipPresenter;

/**
 * Created by xavier on 6/13/16.
 */
public class RelationshipSelectedDialog implements Showable, DialogInterface.OnClickListener  {

  private RelationshipPresenter presenter;

  private Dialog dialog;

  public RelationshipSelectedDialog(RelationshipPresenter presenter, Context context) {
    this.presenter = presenter;

    final CharSequence[] options = {"Edit", "Delete"};
    dialog = new AlertDialog.Builder(context)
      .setTitle(presenter.getSelectedRelationship().toString())
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
        presenter.editRelationship(presenter.getSelectedRelationship());
        break;
      case 1:
        presenter.deleteRelationship(presenter.getSelectedRelationship());
        break;
    }
    dialog.dismiss();
  }
}
