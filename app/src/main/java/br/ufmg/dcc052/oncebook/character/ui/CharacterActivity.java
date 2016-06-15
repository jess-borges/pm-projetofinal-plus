package br.ufmg.dcc052.oncebook.character.ui;

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
import br.ufmg.dcc052.oncebook.character.Character;
import br.ufmg.dcc052.oncebook.character.CharacterPresenter;
import br.ufmg.dcc052.oncebook.character.CharacterView;
import br.ufmg.dcc052.oncebook.relationship.ui.RelationshipActivity;

public class CharacterActivity
  extends AppCompatActivity
  implements CharacterView,
             View.OnClickListener,
             AdapterView.OnItemLongClickListener,
             AdapterView.OnItemClickListener {

  private CharacterAdapter adapter;
  private CharacterPresenter presenter;

  private ListView listView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_characters);

    Intent intent = getIntent();
    int bookId = intent.getIntExtra(OncebookConstants.EXTRA_BOOK_ID, 0);

    presenter = new CharacterPresenter(this, this, bookId);

    listView = (ListView) findViewById(R.id.listViewCharacters);
    listView.setOnItemLongClickListener(this);
    listView.setOnItemClickListener(this);
    presenter.refresh();

    FloatingActionButton buttonCreateCharacter = (FloatingActionButton) findViewById(R.id.buttonCreateCharacter);
    buttonCreateCharacter.setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    presenter.createCharacter();
  }

  @Override
  public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
    Character character = (Character)parent.getItemAtPosition(position);
    presenter.selectCharacter(character);
    return true;
  }

  @Override
  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    Character character = (Character)parent.getItemAtPosition(position);
    Intent intent = new Intent(this, RelationshipActivity.class);
    intent.putExtra(OncebookConstants.EXTRA_CHARACTER_ID, character.getId());
    this.startActivity(intent);
  }

  @Override
  public void showSaveCharacterDialog() {
    new SaveCharacterDialog(presenter, this).show();
  }

  @Override
  public void showCharacterSelectedDialog() {
    new CharacterSelectedDialog(presenter, this).show();
  }

  @Override
  public void showCharacterDeletedToast() {
    Toast
      .makeText(this, "Character deleted successfully", Toast.LENGTH_SHORT)
      .show();
  }

  @Override
  public void onItemsNext(List<Character> characters) {
    if (adapter == null) {
      adapter = new CharacterAdapter(this, characters);
    } else {
      adapter.clear();
      adapter.addAll(characters);
      adapter.notifyDataSetChanged();
    }
    listView.setAdapter(adapter);
  }

  @Override
  public void onItemsError(Throwable error) {
    adapter.notifyDataSetInvalidated();
    Toast
      .makeText(getApplicationContext(), "An error occurred while fetching characters information", Toast.LENGTH_SHORT)
      .show();
  }
}
