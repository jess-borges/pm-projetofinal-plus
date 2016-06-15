package br.ufmg.dcc052.oncebook.book.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import br.ufmg.dcc052.oncebook.R;
import br.ufmg.dcc052.oncebook.book.Book;

/**
 * Created by xavier on 6/6/16.
 */
public class BookAdapter extends ArrayAdapter<Book> {

  public BookAdapter(Context context, List<Book> books) {
    super(context, 0, books);
  }

  @Override
  public View getView(int position, View view, ViewGroup parent) {
    if (view == null) {
      view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
    }

    Book book = getItem(position);

    TextView tvBookName = (TextView) view.findViewById(R.id.tvBookName);
    TextView tvBookDescription = (TextView) view.findViewById(R.id.tvBookDescription);
    /*TextView tvBookNumPages = (TextView) view.findViewById(R.id.tvBookNumPages);*/
    tvBookName.setText(book.getName());
    tvBookDescription.setText(book.getDescription());
    /*tvBookNumPages.setText(Integer.toString(book.getNumberOfPages()) + " pages");*/

    return view;
  }
}
