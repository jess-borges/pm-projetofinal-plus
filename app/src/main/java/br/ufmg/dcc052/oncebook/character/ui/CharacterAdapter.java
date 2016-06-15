package br.ufmg.dcc052.oncebook.character.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import br.ufmg.dcc052.oncebook.R;
import br.ufmg.dcc052.oncebook.character.Character;

/**
 * Created by xavier on 6/6/16.
 */
public class CharacterAdapter extends ArrayAdapter<Character> {

  public CharacterAdapter(Context context, List<Character> characters) {
    super(context, 0, characters);
  }

  @Override
  public View getView(int position, View view, ViewGroup parent) {
    if (view == null) {
      view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_character, parent, false);
    }

    Character character = getItem(position);

    TextView tvCharacterName = (TextView) view.findViewById(R.id.tvCharacterName);
    TextView tvCharacterDescription = (TextView) view.findViewById(R.id.tvCharacterDescription);
    tvCharacterName.setText(character.getName());
    tvCharacterDescription.setText(character.getDescription());

    return view;
  }
}
