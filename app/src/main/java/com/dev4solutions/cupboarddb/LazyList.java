package com.dev4solutions.cupboarddb;

import android.database.Cursor;

import java.util.ArrayList;

/**
 * The type Lazy list.
 *
 * @param <T> the type parameter
 */
public class LazyList<T> extends ArrayList<T> {

    private final Cursor mCursor;
    private final ItemFactory<T> mCreator;

    /**
     * Instantiates a new Lazy list.
     *
     * @param cursor  the cursor
     * @param creator the creator
     */
    public LazyList(Cursor cursor, ItemFactory<T> creator) {
        mCursor = cursor;
        mCreator = creator;
    }

    @Override
    public T get(int index) {
        int size = super.size();
        if (index < size) {
            // find item in the collection
            T item = super.get(index);
            if (item == null) {
                item = mCreator.create(mCursor, index);
                set(index, item);
            }
            return item;
        } else {
            // we have to grow the collection
            for (int i = size; i < index; i++) {
                add(null);
            }
            // create last object, add and return
            T item = mCreator.create(mCursor, index);
            add(item);
            return item;
        }
    }

    @Override
    public int size() {
        return mCursor.getCount();
    }

    /**
     * Close cursor.
     */
    public void closeCursor() {
        mCursor.close();
    }

    /**
     * The interface Item factory.
     *
     * @param <T> the type parameter
     */
    public interface ItemFactory<T> {
        /**
         * Create t.
         *
         * @param cursor the cursor
         * @param index  the index
         * @return the t
         */
        T create(Cursor cursor, int index);
    }
}