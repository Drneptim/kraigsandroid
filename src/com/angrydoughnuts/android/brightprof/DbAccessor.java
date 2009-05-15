/****************************************************************************
 * Copyright 2009 kraigs.android@gmail.com
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 ****************************************************************************/

package com.angrydoughnuts.android.brightprof;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DbAccessor {
  private DbHelper db;
  private SQLiteDatabase rDb;
  private SQLiteDatabase rwDb;

  public DbAccessor(Context context) {
    db = new DbHelper(context);
    rDb = db.getReadableDatabase();
    rwDb = db.getWritableDatabase();
  }

  public Cursor getAll() {
    return rDb.query(DbHelper.DB_TABLE, new String[] { DbHelper.PROF_ID_COL,
        DbHelper.PROF_NAME_COL, DbHelper.PROF_VALUE_COL }, null, null, null,
        null, null);
  }

  public void updateProfile(int rowId, String name, int brightness) {
    ContentValues values = new ContentValues(2);
    values.put(DbHelper.PROF_NAME_COL, name);
    values.put(DbHelper.PROF_VALUE_COL, brightness);
    // If this is an unknown row id, create a new row.
    if (rowId < 0) {
      rwDb.insert(DbHelper.DB_TABLE, null, values);
      // Otherwise, update the supplied row id.
    } else {
      String where = DbHelper.PROF_ID_COL + " = " + rowId;
      rwDb.update(DbHelper.DB_TABLE, values, where, null);
    }
  }

  public void deletProfile(int rowId) {
    String where = DbHelper.PROF_ID_COL + " = " + rowId;
    rwDb.delete(DbHelper.DB_TABLE, where, null);
  }
}
