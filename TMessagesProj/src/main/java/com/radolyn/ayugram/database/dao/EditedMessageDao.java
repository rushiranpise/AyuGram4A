package com.radolyn.ayugram.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.radolyn.ayugram.database.entities.EditedMessage;

import java.util.List;

@Dao
public interface EditedMessageDao {
    @Query("SELECT * FROM editedmessage WHERE userId = :userId AND dialogId = :dialogId AND messageId = :messageId ORDER BY date")
    List<EditedMessage> getAllRevisions(long userId, long dialogId, long messageId);

    @Query("SELECT * FROM editedmessage WHERE userId = :userId AND dialogId = :dialogId AND messageId = :messageId AND date = :date")
    EditedMessage getRevision(long userId, long dialogId, long messageId, long date);

    @Insert
    void insert(EditedMessage revision);
}
