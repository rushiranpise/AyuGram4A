/*

 This is the source code of exteraGram for Android.

 We do not and cannot prevent the use of our code,
 but be respectful and credit the original author.

 Copyright @immat0x1, 2023

*/

package com.radolyn.ayugram.preferences;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.exteragram.messenger.ExteraConfig;
import com.exteragram.messenger.preferences.BasePreferencesActivity;

import org.telegram.ui.Cells.HeaderCell;
import org.telegram.ui.Cells.TextCheckCell;

public class AyuGramPreferencesActivity extends BasePreferencesActivity {
    private int ghostEssentialsHeaderRow;
    private int sendReadPacketsRow;
    private int sendOnlinePacketsRow;
    private int sendUploadProgressRow;
    private int sendOfflinePacketAfterOnlineRow;
    private int markReadAfterSendRow;
    private int useScheduledMessagesRow;

    private int spyHeaderRow;
    private int keepDeletedMessagesRow;
    private int keepMessagesHistoryRow;

    private int qolHeaderRow;
    private int realForwardTime;
    private int showFromChannel;
    private int keepAliveService;
    private int walModeRow;

    @Override
    protected void updateRowsId() {
        super.updateRowsId();

        ghostEssentialsHeaderRow = newRow();
        sendReadPacketsRow = newRow();
        sendOnlinePacketsRow = newRow();
        sendUploadProgressRow = newRow();
        sendOfflinePacketAfterOnlineRow = newRow();
        markReadAfterSendRow = newRow();
        useScheduledMessagesRow = newRow();

        spyHeaderRow = newRow();
        keepDeletedMessagesRow = newRow();
        keepMessagesHistoryRow = newRow();

        qolHeaderRow = newRow();
        realForwardTime = newRow();
        showFromChannel = newRow();
        keepAliveService = newRow();
        walModeRow = newRow();
    }

    @Override
    protected void onItemClick(View view, int position, float x, float y) {
        if (position == sendReadPacketsRow) {
            ExteraConfig.editor.putBoolean("sendReadPackets", ExteraConfig.sendReadPackets ^= true).apply();
            ((TextCheckCell) view).setChecked(ExteraConfig.sendReadPackets);
        } else if (position == sendOnlinePacketsRow) {
            ExteraConfig.editor.putBoolean("sendOnlinePackets", ExteraConfig.sendOnlinePackets ^= true).apply();
            ((TextCheckCell) view).setChecked(ExteraConfig.sendOnlinePackets);
        } else if (position == sendUploadProgressRow) {
            ExteraConfig.editor.putBoolean("sendUploadProgress", ExteraConfig.sendUploadProgress ^= true).apply();
            ((TextCheckCell) view).setChecked(ExteraConfig.sendUploadProgress);
        } else if (position == sendOfflinePacketAfterOnlineRow) {
            ExteraConfig.editor.putBoolean("sendOfflinePacketAfterOnline", ExteraConfig.sendOfflinePacketAfterOnline ^= true).apply();
            ((TextCheckCell) view).setChecked(ExteraConfig.sendOfflinePacketAfterOnline);
        } else if (position == markReadAfterSendRow) {
            ExteraConfig.editor.putBoolean("markReadAfterSend", ExteraConfig.markReadAfterSend ^= true).apply();
            ((TextCheckCell) view).setChecked(ExteraConfig.markReadAfterSend);
        } else if (position == useScheduledMessagesRow) {
            ExteraConfig.editor.putBoolean("useScheduledMessages", ExteraConfig.useScheduledMessages ^= true).apply();
            ((TextCheckCell) view).setChecked(ExteraConfig.useScheduledMessages);
        } else if (position == keepDeletedMessagesRow) {
            ExteraConfig.editor.putBoolean("keepDeletedMessages", ExteraConfig.keepDeletedMessages ^= true).apply();
            ((TextCheckCell) view).setChecked(ExteraConfig.keepDeletedMessages);
        } else if (position == keepMessagesHistoryRow) {
            ExteraConfig.editor.putBoolean("keepMessagesHistory", ExteraConfig.keepMessagesHistory ^= true).apply();
            ((TextCheckCell) view).setChecked(ExteraConfig.keepMessagesHistory);
        } else if (position == realForwardTime) {
            ExteraConfig.editor.putBoolean("realForwardTime", ExteraConfig.realForwardTime ^= true).apply();
            ((TextCheckCell) view).setChecked(ExteraConfig.realForwardTime);
        } else if (position == showFromChannel) {
            ExteraConfig.editor.putBoolean("showFromChannel", ExteraConfig.showFromChannel ^= true).apply();
            ((TextCheckCell) view).setChecked(ExteraConfig.showFromChannel);
        } else if (position == keepAliveService) {
            ExteraConfig.editor.putBoolean("keepAliveService", ExteraConfig.keepAliveService ^= true).apply();
            ((TextCheckCell) view).setChecked(ExteraConfig.keepAliveService);
        } else if (position == walModeRow) {
            ExteraConfig.editor.putBoolean("walMode", ExteraConfig.walMode ^= true).apply();
            ((TextCheckCell) view).setChecked(ExteraConfig.walMode);
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
                    if (position == ghostEssentialsHeaderRow) {
                        headerCell.setText("Ghost essentials");
                    } else if (position == spyHeaderRow) {
                        headerCell.setText("Spy essentials");
                    } else if (position == qolHeaderRow) {
                        headerCell.setText("QoL toggles");
                    }
                    break;
                case 5:
                    TextCheckCell textCheckCell = (TextCheckCell) holder.itemView;
                    textCheckCell.setEnabled(true, null);
                    if (position == sendReadPacketsRow) {
                        textCheckCell.setTextAndCheck("Send read status", ExteraConfig.sendReadPackets, true);
                    } else if (position == sendOnlinePacketsRow) {
                        textCheckCell.setTextAndCheck("Send online status", ExteraConfig.sendOnlinePackets, true);
                    } else if (position == sendUploadProgressRow) {
                        textCheckCell.setTextAndCheck("Send typing & upload status", ExteraConfig.sendUploadProgress, true);
                    } else if (position == sendOfflinePacketAfterOnlineRow) {
                        textCheckCell.setTextAndCheck("Immediate offline after online β", ExteraConfig.sendOfflinePacketAfterOnline, true);
                    } else if (position == markReadAfterSendRow) {
                        textCheckCell.setTextAndCheck("Send read status after reply β", ExteraConfig.markReadAfterSend, true);
                    } else if (position == useScheduledMessagesRow) {
                        textCheckCell.setTextAndCheck("Schedule messages β", ExteraConfig.useScheduledMessages, true);
                    } else if (position == keepDeletedMessagesRow) {
                        textCheckCell.setTextAndCheck("Keep deleted messages β", ExteraConfig.keepDeletedMessages, true);
                    } else if (position == keepMessagesHistoryRow) {
                        textCheckCell.setTextAndCheck("Keep edits history β", ExteraConfig.keepMessagesHistory, true);
                    } else if (position == realForwardTime) {
                        textCheckCell.setTextAndCheck("Show real forward time", ExteraConfig.realForwardTime, true);
                    } else if (position == showFromChannel) {
                        textCheckCell.setTextAndCheck("Show «channel» label", ExteraConfig.showFromChannel, true);
                    } else if (position == keepAliveService) {
                        textCheckCell.setTextAndCheck("Keep Alive Service β", ExteraConfig.keepAliveService, true);
                    } else if (position == walModeRow) {
                        textCheckCell.setTextAndCheck("Enable WAL mode", ExteraConfig.walMode, true);
                    }
                    break;
            }
        }

        @Override
        public int getItemViewType(int position) {
            if (
                    position == ghostEssentialsHeaderRow ||
                            position == spyHeaderRow ||
                            position == qolHeaderRow
            ) {
                return 3;
            }
            return 5;
        }
    }
}
