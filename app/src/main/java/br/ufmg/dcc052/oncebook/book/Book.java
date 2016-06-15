package br.ufmg.dcc052.oncebook.book;

import android.support.annotation.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.ufmg.dcc052.oncebook.character.Character;

/**
 * Created by xavier on 6/6/16.
 */
public class Book implements Serializable {

  private int id;
  private String name;
  @Nullable
  private String description;
  @Nullable
  private List<Character> characters;
  private int numberOfPages;
  private int currentPage;

  public Book(int id) { this(id, "", "", 0); }
  public Book(String name) {
    this(name, "", 0);
  }

  public Book(String name, String description, int numberOfPages, int currentPage) {
    this.name = name;
    this.description = description;
    this.numberOfPages = numberOfPages;
    this.currentPage = currentPage;
  }

  public Book(String name, String description, int numberOfPages) {
    this(name, description, numberOfPages, 0);
  }

  public Book(int id, String name, String description, int numberOfPages) {
    this(name, description, numberOfPages);
    this.id = id;
  }

  public Book(int id, String name, String description, int numberOfPages, int currentPage) {
    this (id, name, description, numberOfPages);
    this.currentPage = currentPage;
  }

  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }

  public List<Character> getCharacters() {
    return characters;
  }

  public int getNumberOfPages() { return numberOfPages; }
  public void setNumberOfPages(int numberOfPages) { this.numberOfPages = numberOfPages; }

  public int getCurrentPage() { return currentPage; }
  public void setCurrentPage(int currentPage) { this.currentPage = currentPage; }
}
