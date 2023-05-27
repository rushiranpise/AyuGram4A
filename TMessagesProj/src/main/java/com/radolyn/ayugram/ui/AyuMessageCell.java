package com.radolyn.ayugram.ui;

import android.content.Context;

import org.telegram.messenger.DocumentObject;
import org.telegram.messenger.R;
import org.telegram.messenger.SvgHelper;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Cells.ChatMessageCell;
import org.telegram.ui.Components.ClipRoundedDrawable;

public class AyuMessageCell extends ChatMessageCell {
    private final ClipRoundedDrawable locationLoadingThumb;
    private final boolean loadedAttachment;

    public AyuMessageCell(Context context) {
        super(context);

        setFullyDraw(true);
        isChat = false;
        setDelegate(new ChatMessageCell.ChatMessageCellDelegate() {
        });

        SvgHelper.SvgDrawable svgThumb = DocumentObject.getSvgThumb(R.raw.map_placeholder, Theme.key_chat_outLocationIcon, (Theme.isCurrentThemeDark() ? 3 : 6) * .12f);
        svgThumb.setAspectCenter(true);
        locationLoadingThumb = new ClipRoundedDrawable(svgThumb);

        loadedAttachment = false;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        // dirty hack to load our image instead of default one
        if (!loadedAttachment && getMessageObject().useCustomPhoto) {
            getPhotoImage().setImage(getMessageObject().messageOwner.attachPath, null, locationLoadingThumb, null, 0);
        }
    }
}
