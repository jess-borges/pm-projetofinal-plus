package br.ufmg.dcc052.oncebook.relationship.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import br.ufmg.dcc052.oncebook.R;
import br.ufmg.dcc052.oncebook.relationship.Relationship;

/**
 * Created by xavier on 6/6/16.
 */
public class RelationshipAdapter extends ArrayAdapter<Relationship> {

  public RelationshipAdapter(Context context, List<Relationship> relationships) {
    super(context, 0, relationships);
  }

  @Override
  public View getView(int position, View view, ViewGroup parent) {
    if (view == null) {
      view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_relationship, parent, false);
    }

    Relationship relationship = getItem(position);

    TextView tvRelationship = (TextView) view.findViewById(R.id.tvRelationship);
    tvRelationship.setText(relationship.getName() + " of " + relationship.getSecondCharacter().getName());

    return view;
  }
}
