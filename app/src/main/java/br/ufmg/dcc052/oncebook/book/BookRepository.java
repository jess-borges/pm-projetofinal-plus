package br.ufmg.dcc052.oncebook.book;

import java.util.List;

import br.ufmg.dcc052.oncebook.book.Book;
import br.ufmg.dcc052.oncebook.data.Repository;

/**
 * Created by xavier on 6/6/16.
 */
public interface BookRepository extends Repository<Book, Integer> {
  List<Book> findByName(String name);
}
