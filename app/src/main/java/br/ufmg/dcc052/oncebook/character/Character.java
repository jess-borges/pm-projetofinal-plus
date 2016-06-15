package br.ufmg.dcc052.oncebook.character;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

import br.ufmg.dcc052.oncebook.book.Book;
import br.ufmg.dcc052.oncebook.relationship.Relationship;

/**
 * Created by xavier on 6/6/16.
 */
public class Character {
/*new functions branch - 15-06*/
  private int id;
  private String name;
  private String description;
  private Book book;
  private int appearancePage;
  private Bitmap picture;
  private List<Relationship> relationships;

  public Character(String name) {
    this(name, "", null, 0, null);
  }

  public Character(String name, String description, Book book) { this(name, description, book, 0, null); }

  public Character() { }
  public Character(String name, String description, Book book, int appearancePage, Bitmap picture) {
    this.name = name;
    this.description = description;
    this.book = book;
    this.appearancePage = appearancePage;
    this.picture = picture;
    this.relationships = new ArrayList<Relationship>();
  }

  public Character(int id, String name, String description, Book book, int appearancePage, Bitmap picture) {
    this(name, description, book, appearancePage, picture);
    this.id = id;
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

  public Book getBook() {
    return book;
  }
  public void setBook(Book book) {
    this.book = book;
  }

  public int getAppearancePage() {
    return appearancePage;
  }
  public void setAppearancePage(int appearancePage) {
    this.appearancePage = appearancePage;
  }

  public Bitmap getPicture() {
    return picture;
  }
  public void setPicture(Bitmap picture) {
    this.picture = picture;
  }

  public List<Relationship> getRelationships() {
    return relationships;
  }
}
