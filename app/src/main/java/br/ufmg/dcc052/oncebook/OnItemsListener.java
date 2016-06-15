package br.ufmg.dcc052.oncebook;

import java.util.List;

/**
 * Created by xavier on 6/14/16.
 */
public interface OnItemsListener<T> {
  void onItemsNext(List<T> items);
  void onItemsError(Throwable error);
}
