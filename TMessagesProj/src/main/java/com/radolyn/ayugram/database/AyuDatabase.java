package com.radolyn.ayugram.database;

import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.radolyn.ayugram.database.dao.EditedMessageDao;
import com.radolyn.ayugram.database.entities.EditedMessage;

@Database(entities = {EditedMessage.class}, version = 2, autoMigrations = {
        @AutoMigration(from = 1, to = 2)
})
public abstract class AyuDatabase extends RoomDatabase {
    public abstract EditedMessageDao editedMessageDao();
}