package br.ufmg.dcc052.oncebook.character;

import java.lang.*;

import br.ufmg.dcc052.oncebook.OnItemsListener;

/**
 * Created by xavier on 6/14/16.
 */
public interface CharacterView extends OnItemsListener<Character> {
  void showSaveCharacterDialog();
  void showCharacterSelectedDialog();
  void showCharacterDeletedToast();
}
