package br.ufmg.dcc052.oncebook.relationship;

import java.util.List;

import br.ufmg.dcc052.oncebook.character.Character;
import br.ufmg.dcc052.oncebook.data.Repository;

/**
 * Created by xavier on 6/6/16.
 */
public interface RelationshipRepository extends Repository<Relationship, Integer> {
  List<Relationship> getAllByCharacter(Character character);
}

