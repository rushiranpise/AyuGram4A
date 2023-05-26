package com.radolyn.ayugram.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.exteragram.messenger.ExteraConfig;
import com.radolyn.ayugram.database.entities.EditedMessage;

import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.ImageLocation;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MediaController;
import org.telegram.messenger.R;
import org.telegram.tgnet.TLRPC;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Cells.DialogCell;
import org.telegram.ui.Cells.HeaderCell;
import org.telegram.ui.Cells.PhotoAttachPhotoCell;
import org.telegram.ui.Cells.PhotoPickerPhotoCell;
import org.telegram.ui.Cells.TextBlockCell;
import org.telegram.ui.Cells.TextDetailCell;
import org.telegram.ui.Components.BackupImageView;
import org.telegram.ui.Components.BulletinFactory;
import org.telegram.ui.Components.ChatAttachAlertPhotoLayoutPreview;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.PhotoViewerWebView;
import org.telegram.ui.PhotoViewer;

public class AyuMessageDetailCell extends LinearLayout {

    private final HeaderCell dateView;
    private final TextBlockCell textView;
    private final TextBlockCell filePathView;
    private final BackupImageView imageView;
    private EditedMessage editedMessage;

    public AyuMessageDetailCell(Context context, BaseFragment fragment, Theme.ResourcesProvider resourcesProvider) {
        super(context);

        setOrientation(LinearLayout.VERTICAL);

        dateView = new HeaderCell(context);
        addView(dateView, LayoutHelper.createFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, LocaleController.isRTL ? Gravity.RIGHT : Gravity.LEFT, 0, 8, 0, 0));

        textView = new TextBlockCell(context);
        textView.setOnClickListener(v -> {
            BulletinFactory.of(fragment).createSimpleBulletin(R.drawable.msg_info, LocaleController.getString("MessageCopied", R.string.MessageCopied)).show();
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) ApplicationLoader.applicationContext.getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("ayuMessageHistory", editedMessage.text);
            clipboard.setPrimaryClip(clip);
        });
        addView(textView, LayoutHelper.createFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, LocaleController.isRTL ? Gravity.RIGHT : Gravity.LEFT, 0, 0, 0, 0));

        imageView = new BackupImageView(context);
        imageView.setAspectFit(true);
        imageView.setRoundRadius(4);

        filePathView = new TextBlockCell(context);
        filePathView.setTextColor(Theme.getColor(Theme.key_dialogTextHint));
        addView(filePathView);
    }

    public void setEditedMessage(EditedMessage editedMessage) {
        this.editedMessage = editedMessage;

        var dateFormatted = LocaleController.formatDateAudio(editedMessage.date, false);

        dateView.setText(dateFormatted);
        textView.setText(editedMessage.text, false);

        if (editedMessage.path != null) {
            filePathView.setText("Path: " + editedMessage.path, false);

            if (editedMessage.isDocument) {

            } else {
                var drawable = Drawable.createFromPath(editedMessage.path);
                imageView.setImageDrawable(drawable);
                addView(imageView, LayoutHelper.createFrame(drawable.getMinimumWidth(), drawable.getMinimumHeight(), Gravity.CENTER, 0, 0, 0, 0));
//                addView(imageView, 2, LayoutHelper.createFrame(getWidth(), drawable.getMinimumHeight(), Gravity.CENTER, 0, 0, 0, 0));
            }
        }
    }
}