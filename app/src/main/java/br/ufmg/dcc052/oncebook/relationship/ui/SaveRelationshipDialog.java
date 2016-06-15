package br.ufmg.dcc052.oncebook.relationship.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.ufmg.dcc052.oncebook.R;
import br.ufmg.dcc052.oncebook.Showable;
import br.ufmg.dcc052.oncebook.character.Character;
import br.ufmg.dcc052.oncebook.relationship.Relationship;
import br.ufmg.dcc052.oncebook.relationship.RelationshipPresenter;

/**
 * Created by xavier on 6/13/16.
 */
public class SaveRelationshipDialog implements Showable, DialogInterface.OnClickListener {

  private RelationshipPresenter presenter;

  private Dialog dialog;
  private EditText etRelationshipName;
  private Spinner spinnerSecondCharacter;

  public SaveRelationshipDialog(RelationshipPresenter presenter, Context context) {
    this.presenter = presenter;

    View formBookView = LayoutInflater.from(context).inflate(R.layout.form_relationship, null, false);
    etRelationshipName = (EditText) formBookView.findViewById(R.id.etRelationshipName);
    spinnerSecondCharacter = (Spinner) formBookView.findViewById(R.id.spinnerSecondCharacter);

    List<Character> characterOptions = presenter.getCharactersFromSameBook();
    if (characterOptions == null || characterOptions.size() == 0) {
      Toast
        .makeText(context, "You can't create a relationship with only one character in the book", Toast.LENGTH_SHORT)
        .show();
      return;
    }

    List<String> spinnerItems = new ArrayList<>();
    ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, spinnerItems);
    for (Character c : characterOptions) {
      spinnerItems.add(c.getName());
    }
    spinnerSecondCharacter.setAdapter(spinnerAdapter);

    Relationship relationship = presenter.getSelectedRelationship();

    if (relationship != null) {
      etRelationshipName.setText(relationship.getName());
      spinnerSecondCharacter.setSelection(spinnerAdapter.getPosition(presenter.getSelectedRelationship().getSecondCharacter().getName()));
    }

    dialog = new AlertDialog.Builder(context)
      .setTitle(relationship == null ? "Create relationship" : "Edit relationship")
      .setView(formBookView)
      .setPositiveButton(relationship == null ? "Create" : "Save", this)
      .create();
  }

  @Override
  public void show() {
    if(dialog != null) {
      dialog.show();
    }
  }

  @Override
  public void onClick(DialogInterface dialog, int which) {
    presenter.saveRelationship(etRelationshipName.getText().toString(), spinnerSecondCharacter.getSelectedItem().toString());
    dialog.dismiss();
  }
}
