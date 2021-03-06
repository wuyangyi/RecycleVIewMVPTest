package com.zz.recycleviewmvptest.bean;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "CHESS_LIST_BEAN".
*/
public class ChessListBeanDao extends AbstractDao<ChessListBean, Long> {

    public static final String TABLENAME = "CHESS_LIST_BEAN";

    /**
     * Properties of entity ChessListBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Create_time = new Property(1, long.class, "create_time", false, "CREATE_TIME");
        public final static Property Start_time = new Property(2, String.class, "start_time", false, "START_TIME");
        public final static Property End_time = new Property(3, String.class, "end_time", false, "END_TIME");
        public final static Property Time_long = new Property(4, String.class, "time_long", false, "TIME_LONG");
        public final static Property Winner = new Property(5, String.class, "winner", false, "WINNER");
    }


    public ChessListBeanDao(DaoConfig config) {
        super(config);
    }
    
    public ChessListBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"CHESS_LIST_BEAN\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"CREATE_TIME\" INTEGER NOT NULL ," + // 1: create_time
                "\"START_TIME\" TEXT," + // 2: start_time
                "\"END_TIME\" TEXT," + // 3: end_time
                "\"TIME_LONG\" TEXT," + // 4: time_long
                "\"WINNER\" TEXT);"); // 5: winner
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"CHESS_LIST_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, ChessListBean entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getCreate_time());
 
        String start_time = entity.getStart_time();
        if (start_time != null) {
            stmt.bindString(3, start_time);
        }
 
        String end_time = entity.getEnd_time();
        if (end_time != null) {
            stmt.bindString(4, end_time);
        }
 
        String time_long = entity.getTime_long();
        if (time_long != null) {
            stmt.bindString(5, time_long);
        }
 
        String winner = entity.getWinner();
        if (winner != null) {
            stmt.bindString(6, winner);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, ChessListBean entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getCreate_time());
 
        String start_time = entity.getStart_time();
        if (start_time != null) {
            stmt.bindString(3, start_time);
        }
 
        String end_time = entity.getEnd_time();
        if (end_time != null) {
            stmt.bindString(4, end_time);
        }
 
        String time_long = entity.getTime_long();
        if (time_long != null) {
            stmt.bindString(5, time_long);
        }
 
        String winner = entity.getWinner();
        if (winner != null) {
            stmt.bindString(6, winner);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public ChessListBean readEntity(Cursor cursor, int offset) {
        ChessListBean entity = new ChessListBean( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getLong(offset + 1), // create_time
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // start_time
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // end_time
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // time_long
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5) // winner
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, ChessListBean entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setCreate_time(cursor.getLong(offset + 1));
        entity.setStart_time(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setEnd_time(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setTime_long(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setWinner(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(ChessListBean entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(ChessListBean entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(ChessListBean entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
