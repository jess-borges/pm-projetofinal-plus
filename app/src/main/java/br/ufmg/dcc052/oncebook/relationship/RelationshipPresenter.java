package br.ufmg.dcc052.oncebook.relationship;

import android.content.Context;

import java.util.List;

import br.ufmg.dcc052.oncebook.Refreshable;
import br.ufmg.dcc052.oncebook.character.Character;
import br.ufmg.dcc052.oncebook.character.CharacterRepository;
import br.ufmg.dcc052.oncebook.character.SQLiteCharacterRepository;

/**
 * Created by xavier on 6/14/16.
 */
public class RelationshipPresenter implements Refreshable {

  private Relationship selectedRelationship;

  private RelationshipRepository relationshipRepository;
  private CharacterRepository characterRepository;
  private RelationshipView view;

  private Character character;
  private List<Character> charactersFromSameBook;

  public RelationshipPresenter(Context context, RelationshipView view, int characterId) {
    this.relationshipRepository = new SQLiteRelationshipRepository(context);
    this.characterRepository = new SQLiteCharacterRepository(context);
    this.view = view;
    this.character = characterRepository.getById(characterId);
    this.charactersFromSameBook = characterRepository.getAllFromSameBook(character);
  }

  public void saveRelationship(String name, String secondCharacterName) {
    Character secondCharacter = characterRepository.getByName(secondCharacterName);
    Relationship relationship = selectedRelationship;
    if(relationship == null) {
      relationship = new Relationship(name, character, secondCharacter);
    } else {
      relationship.setName(name);
      relationship.setSecondCharacter(secondCharacter);
    }
    setSelectedRelationship(null);
    relationshipRepository.save(relationship);
    refresh();
  }

  public void createRelationship() {
    setSelectedRelationship(null);
    view.showSaveRelationshipDialog();
  }

  public void selectRelationship(Relationship relationship) {
    setSelectedRelationship(relationship);
    view.showRelationshipSelectedDialog();
  }

  public void editRelationship(Relationship relationship) {
    setSelectedRelationship(relationship);
    view.showSaveRelationshipDialog();
  }

  public void deleteRelationship(Relationship relationship) {
    setSelectedRelationship(null);
    relationshipRepository.delete(relationship);
    view.showRelationshipDeletedToast();
    refresh();
  }

  @Override
  public void refresh() {
    view.onItemsNext(relationshipRepository.getAllByCharacter(character));
  }

  public Relationship getSelectedRelationship() {
    return selectedRelationship;
  }
  public void setSelectedRelationship(Relationship selectedRelationship) {
    this.selectedRelationship = selectedRelationship;
  }

  public Character getCharacter() {
    return character;
  }
  public void setCharacter(Character character) {
    this.character = character;
    charactersFromSameBook = characterRepository.getAllFromSameBook(character);
    refresh();
  }

  public List<Character> getCharactersFromSameBook() {
    return charactersFromSameBook;
  }
}
