package com.dev4solutions.cupboarddb;

public abstract class CupboardModel {
    private Long _id=-1L;

    /**
     * Gets id.
     *
     * @return the id
     */
    public long getId() {
        if (_id == null) {
            return 0;
        }
        return _id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(long id) {
        this._id = id;
    }
}
