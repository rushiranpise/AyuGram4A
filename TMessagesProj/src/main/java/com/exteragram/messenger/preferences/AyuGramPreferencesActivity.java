/*

 This is the source code of exteraGram for Android.

 We do not and cannot prevent the use of our code,
 but be respectful and credit the original author.

 Copyright @immat0x1, 2023

*/

package com.exteragram.messenger.preferences;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.exteragram.messenger.ExteraConfig;

import org.telegram.messenger.LocaleController;
import org.telegram.messenger.R;
import org.telegram.ui.Cells.HeaderCell;
import org.telegram.ui.Cells.TextCheckCell;

public class AyuGramPreferencesActivity extends BasePreferencesActivity {
    private int generalHeaderRow;
    private int ghostModeRow;
    private int scheduledMessagesRow;

    @Override
    protected void updateRowsId() {
        super.updateRowsId();

        generalHeaderRow = newRow();
        ghostModeRow = newRow();
        scheduledMessagesRow = newRow();
    }

    @Override
    protected void onItemClick(View view, int position, float x, float y) {
        if (position == ghostModeRow) {
            ExteraConfig.editor.putBoolean("ghostMode", ExteraConfig.ghostMode ^= true).apply();
            ((TextCheckCell) view).setChecked(ExteraConfig.ghostMode);
        } else if (position == scheduledMessagesRow) {
            ExteraConfig.editor.putBoolean("scheduleMessages", ExteraConfig.scheduleMessages ^= true).apply();
            ((TextCheckCell) view).setChecked(ExteraConfig.scheduleMessages);
        }
    }

    @Override
    protected String getTitle() {
        return "AyuGram Preferences";
    }

    @Override
    protected BaseListAdapter createAdapter(Context context) {
        return new ListAdapter(context);
    }

    private class ListAdapter extends BaseListAdapter {

        public ListAdapter(Context context) {
            super(context);
        }

        @Override
        public int getItemCount() {
            return rowCount;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, boolean payload) {
            switch (holder.getItemViewType()) {
                case 3:
                    HeaderCell headerCell = (HeaderCell) holder.itemView;
                    if (position == generalHeaderRow) {
                        headerCell.setText(LocaleController.getString("General", R.string.General));
                    }
                    break;
                case 5:
                    TextCheckCell textCheckCell = (TextCheckCell) holder.itemView;
                    textCheckCell.setEnabled(true, null);
                    if (position == ghostModeRow) {
                        textCheckCell.setTextAndCheck("Ghost Mode", ExteraConfig.ghostMode, true);
                    } else if (position == scheduledMessagesRow) {
                        textCheckCell.setTextAndCheck("Schedule Messages", ExteraConfig.scheduleMessages, true);
                    }
                    break;
            }
        }

        @Override
        public int getItemViewType(int position) {
            if (position == generalHeaderRow) {
                return 3;
            }
            return 5;
        }
    }
}
