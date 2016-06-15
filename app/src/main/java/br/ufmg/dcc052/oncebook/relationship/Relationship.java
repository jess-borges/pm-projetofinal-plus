package br.ufmg.dcc052.oncebook.relationship;

import br.ufmg.dcc052.oncebook.character.Character;

/**
 * Created by xavier on 6/6/16.
 */
public class Relationship {

  private String name;
  private Character firstCharacter;
  private Character secondCharacter;

  public Relationship(String name) {
    this.name = name;
  }

  public Relationship(String name, Character firstCharacter, Character secondCharacter) {
    this(name);
    this.firstCharacter = firstCharacter;
    this.secondCharacter = secondCharacter;
  }

  @Override
  public String toString() {
    return name + " of " + secondCharacter.getName();
  }

  public String getName() {
    return this.name;
  }
  public void setName(String name) {
    this.name = name;
  }

  public Character getFirstCharacter() {
    return firstCharacter;
  }
  public void setFirstCharacter(Character firstCharacter) {
    this.firstCharacter = firstCharacter;
  }

  public Character getSecondCharacter() {
    return secondCharacter;
  }
  public void setSecondCharacter(Character secondCharacter) {
    this.secondCharacter = secondCharacter;
  }
}
