package com.exteragram.messenger.boost.encryption;

import com.google.android.exoplayer2.util.Consumer;

import org.telegram.messenger.MessageObject;
import org.telegram.ui.Components.AnchorSpan;

import java.util.ArrayList;

public class EncryptionHelper {
    public static ArrayList<? extends CharSequence> names;

    public static CharSequence encryptMessage(String toString, long dialog_id, int smth, Consumer<String> currentEncryptor) {
        return null;
    }

    public static CharSequence encryptMessage(String toString, long dialog_id, BaseEncryptor smth) {
        return null;
    }

    public static boolean isEncrypted(CharSequence messageText) {
        return false;
    }

    public static MessageObject decryptMessage(MessageObject editingMessageObject) {
        return null;
    }

    public static AnchorSpan getEncryptorBy(long dialog_id) {
        return null;
    }

    public static int getEncryptorTypeFor(long dialog_id) {
        return 0;
    }

    public static void setEncryptorTypeFor(long dialog_id, int i) {

    }

    public static MessageObject decryptMessage(MessageObject selectedObject, MessageObject.GroupedMessages selectedObjectGroup) {
        return null;
    }

    public static boolean isEncrypted(MessageObject editingMessageObject, Object o) {
        return false;
    }
}
