package com.advanced_android.wordoftoday1;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * '오늘의 한마디'의 콘텐트 프로바이더의 공개용 인터페이스 
 */
public interface WordsOfTodayContract {

    public static final String AUTHORITY = "com.advanced_android.wordoftoday1";
    public static final String TABLE_NAME = "wordoftoday";
    public static Uri CONTENT_URI = Uri.parse(ContentResolver.SCHEME_CONTENT + "://" + AUTHORITY + "/" + TABLE_NAME);

    public static final String MIME_TYPE_DIR = "vnd.android.cursor.dir/" + AUTHORITY + "." + TABLE_NAME;
    public static final String MIME_TYPE_ITEM = "vnd.android.cursor.item/" + AUTHORITY + "." + TABLE_NAME;

    public interface WordsOfTodayColumns extends BaseColumns {
        public static final String NAME = "name";
        public static final String WORDS = "words";
        public static final String DATE = "date";
    }
}
