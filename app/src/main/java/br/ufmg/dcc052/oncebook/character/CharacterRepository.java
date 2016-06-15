package br.ufmg.dcc052.oncebook.character;

import java.util.List;

import br.ufmg.dcc052.oncebook.book.Book;
import br.ufmg.dcc052.oncebook.data.Repository;

/**
 * Created by xavier on 6/6/16.
 */
public interface CharacterRepository extends Repository<Character, Integer> {
  Character getByName(String name);
  List<Character> getAllByBook(Book book);
  List<Character> getAllFromSameBook(Character character);
  List<Character> findByName(String name);
}

