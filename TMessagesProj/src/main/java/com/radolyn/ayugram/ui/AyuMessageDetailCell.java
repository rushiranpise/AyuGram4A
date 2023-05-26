package com.radolyn.ayugram.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.radolyn.ayugram.database.entities.EditedMessage;

import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.LocaleController;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.LayoutHelper;

public class AyuMessageDetailCell extends FrameLayout {

    private final TextView dateView;
    private final TextView textView;
    private final ImageView imageView;
    private final Theme.ResourcesProvider resourcesProvider;
    private boolean needDivider;
    private EditedMessage editedMessage;

    public AyuMessageDetailCell(Context context) {
        this(context, null);
    }

    public AyuMessageDetailCell(Context context, Theme.ResourcesProvider resourcesProvider) {
        super(context);
        this.resourcesProvider = resourcesProvider;

        dateView = new TextView(context);
        dateView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText, resourcesProvider));
        dateView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
        dateView.setGravity(LocaleController.isRTL ? Gravity.RIGHT : Gravity.LEFT);
        dateView.setLines(1);
        dateView.setMaxLines(1);
        dateView.setSingleLine(true);
        dateView.setEllipsize(TextUtils.TruncateAt.END);
        dateView.setImportantForAccessibility(IMPORTANT_FOR_ACCESSIBILITY_NO);
        addView(dateView, LayoutHelper.createFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, LocaleController.isRTL ? Gravity.RIGHT : Gravity.LEFT, 23, 8, 23, 0));

        textView = new TextView(context);
        textView.setHorizontallyScrolling(false);
        textView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText2, resourcesProvider));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13);
        textView.setGravity(LocaleController.isRTL ? Gravity.RIGHT : Gravity.LEFT);
        textView.setImportantForAccessibility(IMPORTANT_FOR_ACCESSIBILITY_NO);
        addView(textView, LayoutHelper.createFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, LocaleController.isRTL ? Gravity.RIGHT : Gravity.LEFT, 23, 33, 23, 0));

        imageView = new ImageView(context);
        imageView.setImportantForAccessibility(IMPORTANT_FOR_ACCESSIBILITY_NO);
        imageView.setScaleType(ImageView.ScaleType.CENTER);
        addView(imageView, LayoutHelper.createFrameRelatively(48, 48, Gravity.END | Gravity.CENTER_VERTICAL, 0, 0, 12, 0));
    }

    public void setEditedMessage(EditedMessage editedMessage) {
        this.editedMessage = editedMessage;

        var dateFormatted = LocaleController.formatDateAudio(editedMessage.date, false);

        dateView.setText(dateFormatted);
        textView.setText(editedMessage.text);
    }

    public String getValue() {
        return editedMessage.text;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(
                MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(1, MeasureSpec.UNSPECIFIED)
        );
    }

    public void setImageClickListener(View.OnClickListener clickListener) {
        imageView.setOnClickListener(clickListener);
    }

    @Override
    public void invalidate() {
        super.invalidate();
        dateView.invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (needDivider) {
            canvas.drawLine(
                    LocaleController.isRTL ? 0 : AndroidUtilities.dp(20),
                    getMeasuredHeight() - 1,
                    getMeasuredWidth() - (LocaleController.isRTL ? AndroidUtilities.dp(20) : 0),
                    getMeasuredHeight() - 1,
                    Theme.dividerPaint
            );
        }
    }
}