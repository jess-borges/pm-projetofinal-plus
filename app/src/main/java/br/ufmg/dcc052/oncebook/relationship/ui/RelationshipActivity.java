package br.ufmg.dcc052.oncebook.relationship.ui;

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
import br.ufmg.dcc052.oncebook.relationship.Relationship;
import br.ufmg.dcc052.oncebook.relationship.RelationshipPresenter;
import br.ufmg.dcc052.oncebook.relationship.RelationshipView;

public class RelationshipActivity
  extends AppCompatActivity
  implements RelationshipView,
             View.OnClickListener,
             AdapterView.OnItemLongClickListener {

  private RelationshipAdapter adapter;
  private RelationshipPresenter presenter;

  private ListView listView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_relationships);

    Intent intent = getIntent();
    int characterId = intent.getIntExtra(OncebookConstants.EXTRA_CHARACTER_ID, 0);

    presenter = new RelationshipPresenter(this, this, characterId);

    listView = (ListView) findViewById(R.id.listViewRelationships);
    listView.setAdapter(adapter);
    listView.setOnItemLongClickListener(this);
    presenter.refresh();

    FloatingActionButton buttonCreateRelationship = (FloatingActionButton) findViewById(R.id.buttonCreateRelationship);
    buttonCreateRelationship.setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    presenter.createRelationship();
  }

  @Override
  public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
    Relationship relationship = (Relationship)parent.getItemAtPosition(position);
    presenter.selectRelationship(relationship);
    return true;
  }

  @Override
  public void showSaveRelationshipDialog() {
    new SaveRelationshipDialog(presenter, this).show();
  }

  @Override
  public void showRelationshipSelectedDialog() {
    new RelationshipSelectedDialog(presenter, this).show();
  }

  @Override
  public void showRelationshipDeletedToast() {
    Toast
      .makeText(this, "Relationship deleted successfully", Toast.LENGTH_SHORT)
      .show();
  }

  @Override
  public void onItemsNext(List<Relationship> relationships) {
    if (adapter == null) {
      adapter = new RelationshipAdapter(this, relationships);
    } else {
      adapter.clear();
      adapter.addAll(relationships);
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
