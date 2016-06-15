package br.ufmg.dcc052.oncebook.character.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import br.ufmg.dcc052.oncebook.R;
import br.ufmg.dcc052.oncebook.Showable;
import br.ufmg.dcc052.oncebook.character.Character;
import br.ufmg.dcc052.oncebook.character.CharacterPresenter;

/**
 * Created by xavier on 6/13/16.
 */
public class SaveCharacterDialog implements Showable, DialogInterface.OnClickListener {

  private CharacterPresenter presenter;

  private Dialog dialog;
  private EditText etCharacterName;
  private EditText etCharacterDescription;

  public SaveCharacterDialog(CharacterPresenter presenter, Context context) {
    this.presenter = presenter;

    View formBookView = LayoutInflater.from(context).inflate(R.layout.form_character, null, false);
    etCharacterName = (EditText) formBookView.findViewById(R.id.etCharacterName);
    etCharacterDescription = (EditText) formBookView.findViewById(R.id.etCharacterDescription);

    Character character = presenter.getSelectedCharacter();

    if (character != null) {
      etCharacterName.setText(character.getName());
      etCharacterDescription.setText(character.getDescription());
    }

    dialog = new AlertDialog.Builder(context)
      .setTitle(character == null ? "Create character" : "Edit character")
      .setView(formBookView)
      .setPositiveButton(character == null ? "Create" : "Save", this)
      .create();
  }

  @Override
  public void show() {
    dialog.show();
  }

  @Override
  public void onClick(DialogInterface dialog, int which) {
    Character character = presenter.getSelectedCharacter();

    if(character == null) {
      character = new Character(etCharacterName.getText().toString(), etCharacterDescription.getText().toString(), presenter.getBook());
    } else {
      character.setName(etCharacterName.getText().toString());
      character.setDescription(etCharacterDescription.getText().toString());
    }

    presenter.saveCharacter(character);
    dialog.dismiss();
  }
}
