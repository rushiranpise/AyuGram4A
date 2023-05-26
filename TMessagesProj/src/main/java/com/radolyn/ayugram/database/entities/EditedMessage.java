package com.radolyn.ayugram.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(primaryKeys = {"userId", "dialogId", "messageId", "date"})
public class EditedMessage {
    public long userId;
    public long dialogId;
    public long messageId;

    @ColumnInfo(name = "text")
    public String text;
    @ColumnInfo(name = "type")
    public boolean isDocument;
    @ColumnInfo(name = "path")
    public String path;
    @ColumnInfo(name = "date")
    public long date;
}
