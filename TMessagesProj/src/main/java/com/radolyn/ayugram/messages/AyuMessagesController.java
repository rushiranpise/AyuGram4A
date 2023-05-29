package com.radolyn.ayugram.messages;

import android.os.Environment;

import androidx.room.Room;

import com.exteragram.messenger.ExteraConfig;
import com.google.android.exoplayer2.util.Log;
import com.radolyn.ayugram.database.AyuDatabase;
import com.radolyn.ayugram.database.entities.EditedMessage;

import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.BuildVars;
import org.telegram.messenger.FileLoader;
import org.telegram.messenger.MessageObject;
import org.telegram.tgnet.TLRPC;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class AyuMessagesController {
    public static final String attachmentsSubfolder = "Saved Attachments";
    private static final File attachmentsPath = new File(
            new File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), BuildVars.APP_NAME),
            attachmentsSubfolder
    );
    private static AyuMessagesController instance;
    private final AyuDatabase database;

    private AyuMessagesController() {
        // recreate for testing if debug
        if (ExteraConfig.getLogging()) {
            ApplicationLoader.applicationContext.deleteDatabase("ayu-data");
            if (attachmentsPath.exists()) {
                attachmentsPath.delete();
            }
        }

        if (!attachmentsPath.exists()) {
            attachmentsPath.mkdirs();
            try {
                new File(attachmentsPath, ".nomedia").createNewFile();
            } catch (IOException e) {
                // ignored, I hate java
            }
        }

        database = Room.databaseBuilder(ApplicationLoader.applicationContext, AyuDatabase.class, "ayu-data")
                .allowMainThreadQueries()
                .build();
    }

    public static AyuMessagesController getInstance() {
        if (instance == null) {
            instance = new AyuMessagesController();
        }
        return instance;
    }

    public boolean hasAnyRevisions(long userId, long dialogId, int msgId) {
        return database.editedMessageDao().hasAnyRevisions(userId, dialogId, msgId);
    }

    public boolean hasAnyRevisionsByGroupId(long userId, long dialogId, long groupId) {
        return database.editedMessageDao().hasAnyRevisionsByGroupId(userId, dialogId, groupId);
    }

    public void onMessageEdited(TLRPC.Message oldMessage, TLRPC.Message newMessage, long userId, int accountId, int currentTime) {
        if (!ExteraConfig.keepMessagesHistory) {
            return;
        }

        boolean sameMedia = false;
        boolean isDocument = false;
        if (oldMessage.media instanceof TLRPC.TL_messageMediaPhoto && newMessage.media instanceof TLRPC.TL_messageMediaPhoto && oldMessage.media.photo != null && newMessage.media.photo != null) {
            sameMedia = oldMessage.media.photo.id == newMessage.media.photo.id;
        } else if (oldMessage.media instanceof TLRPC.TL_messageMediaDocument && newMessage.media instanceof TLRPC.TL_messageMediaDocument && oldMessage.media.document != null && newMessage.media.document != null) {
            sameMedia = oldMessage.media.document.id == newMessage.media.document.id;
            isDocument = true;
        }

        var revision = new EditedMessage();

        var attachPathFile = FileLoader.getInstance(accountId).getPathToMessage(oldMessage);

        if (!sameMedia && attachPathFile.exists()) {
            var filename = attachPathFile.getName();
            var dest = new File(attachmentsPath, filename);

            // copy file, because it's likely to be deleted by Telegram in a few seconds
            boolean success;
            try {
                success = AndroidUtilities.copyFile(attachPathFile, dest);
            } catch (IOException e) {
                Log.d("AyuGram", e.toString());
                success = false;
            }

            if (success) {
                attachPathFile = new File(dest.getAbsolutePath());
            } else {
                attachPathFile = new File("/");
            }
        }

        var attachPath = attachPathFile.getAbsolutePath();

        revision.path = attachPath.equals("/") ? null : attachPath;
        revision.isDocument = isDocument;

        var dao = database.editedMessageDao();

        var dialogId = MessageObject.getDialogId(oldMessage);
        var messageId = newMessage.id;
        var groupId = newMessage.grouped_id;

        if (!sameMedia && revision.path != null && dao.isFirstRevisionWithChangedMedia(userId, dialogId, messageId)) {
            // update previous revisions to reflect media change
            // like, there's no previous file, so replace it with one we copied before...
            dao.updateAttachmentForRevisionsBeforeDate(userId, dialogId, messageId, attachPath, currentTime);
        }

        revision.userId = userId;
        revision.dialogId = dialogId;
        revision.messageId = messageId;
        revision.groupId = groupId;
        revision.text = oldMessage.message;
        revision.date = currentTime;

        dao.insert(revision);
    }

    public List<EditedMessage> getRevisions(long userId, long dialogId, int msgId) {
        return database.editedMessageDao().getAllRevisions(userId, dialogId, msgId);
    }

    public List<EditedMessage> getRevisionsByGroupId(long userId, long dialogId, long groupId) {
        return database.editedMessageDao().getAllRevisionsByGroupId(userId, dialogId, groupId);
    }
}
