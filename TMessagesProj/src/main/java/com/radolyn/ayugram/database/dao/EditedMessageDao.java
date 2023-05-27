package com.radolyn.ayugram.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.radolyn.ayugram.database.entities.EditedMessage;
import com.radolyn.ayugram.messages.AyuMessagesController;

import java.util.List;

@Dao
public interface EditedMessageDao {
    @Query("SELECT * FROM editedmessage WHERE userId = :userId AND dialogId = :dialogId AND messageId = :messageId ORDER BY date")
    List<EditedMessage> getAllRevisions(long userId, long dialogId, long messageId);

    @Query("UPDATE editedmessage SET path = :newPath WHERE userId = :userId AND dialogId = :dialogId AND messageId = :messageId AND date < :beforeDate")
    void updateAttachmentForRevisionsBeforeDate(long userId, long dialogId, long messageId, String newPath, long beforeDate);

    @Query("SELECT NOT EXISTS(SELECT * FROM editedmessage WHERE userId = :userId AND dialogId = :dialogId AND messageId = :messageId AND path LIKE '%" + AyuMessagesController.attachmentsSubfolder + "%')")
    boolean isFirstRevisionWithChangedMedia(long userId, long dialogId, long messageId);

    @Query("SELECT EXISTS(SELECT * FROM editedmessage WHERE userId = :userId AND dialogId = :dialogId AND messageId = :messageId)")
    boolean hasAnyRevisions(long userId, long dialogId, long messageId);

    @Query("SELECT * FROM editedmessage WHERE userId = :userId AND dialogId = :dialogId AND messageId = :messageId AND date = :date")
    EditedMessage getRevision(long userId, long dialogId, long messageId, long date);

    @Insert
    void insert(EditedMessage revision);
}
