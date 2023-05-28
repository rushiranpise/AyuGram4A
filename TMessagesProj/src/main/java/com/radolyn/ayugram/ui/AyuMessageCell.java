package com.radolyn.ayugram.ui;

import android.app.Activity;
import android.content.Context;

import com.radolyn.ayugram.database.entities.EditedMessage;

import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.DocumentObject;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.R;
import org.telegram.messenger.SvgHelper;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Cells.ChatMessageCell;
import org.telegram.ui.Components.BulletinFactory;
import org.telegram.ui.Components.ClipRoundedDrawable;

public class AyuMessageCell extends ChatMessageCell {
    private final ClipRoundedDrawable locationLoadingThumb;
    private EditedMessage editedMessage;

    public AyuMessageCell(Context context, Activity activity, BaseFragment fragment) {
        super(context);

        setFullyDraw(true);
        isChat = false;
        setDelegate(new ChatMessageCell.ChatMessageCellDelegate() {
        });

        SvgHelper.SvgDrawable svgThumb = DocumentObject.getSvgThumb(R.raw.map_placeholder, Theme.key_chat_outLocationIcon, (Theme.isCurrentThemeDark() ? 3 : 6) * .12f);
        svgThumb.setAspectCenter(true);
        locationLoadingThumb = new ClipRoundedDrawable(svgThumb);

        setOnClickListener(v -> {
            // copy only if no media
            if (editedMessage.path == null && editedMessage.text != null && !editedMessage.text.equals("")) {
                AndroidUtilities.addToClipboard(editedMessage.text);
                BulletinFactory.of(fragment).createCopyBulletin(LocaleController.getString("MessageCopied", R.string.MessageCopied)).show();
            }

            // ..open media otherwise
            if (editedMessage.path != null) {
                AndroidUtilities.openForView(getMessageObject(), activity, null);
            }
        });

        setOnLongClickListener(v -> {
            if (editedMessage.text != null && !editedMessage.text.equals("")) {
                AndroidUtilities.addToClipboard(editedMessage.text);
                BulletinFactory.of(fragment).createCopyBulletin(LocaleController.getString("MessageCopied", R.string.MessageCopied)).show();
            }

            return true;
        });
    }

    public void setEditedMessage(EditedMessage editedMessage) {
        this.editedMessage = editedMessage;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        // dirty hack to load our image instead of default one
        if (getMessageObject().useCustomPhoto) {
            getPhotoImage().setImage(getMessageObject().messageOwner.attachPath, null, locationLoadingThumb, null, 0);
        }

        if (editedMessage.path != null && !editedMessage.path.equals("")) {
            getMessageObject().isDownloadingFile = false;
            getMessageObject().loadingCancelled = true;
        }
    }
}
