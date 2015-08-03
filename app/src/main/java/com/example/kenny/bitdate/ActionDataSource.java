package com.example.kenny.bitdate;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kenny on 2015-07-27.
 */
public class ActionDataSource {

    public static final String TABLE_NAME = "Action";
    public static final String COLUMN_BY_USER = "byUser";
    public static final String COLUMN_TO_USER = "toUser";
    private static final String COLUMN_TYPE = "type";
    private static final String COLUMN_UPDATED_AT = "updatedAt";

    private static final String TYPE_LIKED = "liked";
    private static final String TYPE_MATCHED = "matched";
    private static final String TYPE_SKIPPED = "skipped";

    public static void saveUserLiked(final String userId){
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(TABLE_NAME);
        query.whereEqualTo(COLUMN_TO_USER, ParseUser.getCurrentUser().getObjectId());
        query.whereEqualTo(COLUMN_BY_USER, userId);
        query.whereEqualTo(COLUMN_TYPE, TYPE_LIKED);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                ParseObject action = null;
                if (e == null && list.size() > 0){
                    ParseObject otherAction =  list.get(0);
                    otherAction.put(COLUMN_TYPE, TYPE_MATCHED);
                    otherAction.saveInBackground();
                    action = createAction(userId, TYPE_MATCHED);
                }else {
                    action = createAction(userId, TYPE_LIKED);
                }
                action.saveInBackground();
            }
        });
    }

    public static void saveUserSkipped(String userId){
        ParseObject action = createAction(userId, TYPE_SKIPPED);
        action.saveInBackground();
    }

    private static ParseObject createAction(String userId, String type) {
        ParseObject action = new ParseObject(TABLE_NAME);
        action.put(COLUMN_BY_USER, ParseUser.getCurrentUser().getObjectId());
        action.put(COLUMN_TO_USER, userId);
        action.put(COLUMN_TYPE, type);
        return action;
    }

    public static void getMatches(final ActionDataCallbacks callbacks){
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(TABLE_NAME);
        query.whereEqualTo(COLUMN_BY_USER, ParseUser.getCurrentUser().getObjectId());
        query.whereEqualTo(COLUMN_TYPE, TYPE_MATCHED);
        query.orderByDescending(COLUMN_UPDATED_AT);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null){
                    List<String> ids = new ArrayList<String>();
                    for (ParseObject parseObject: list){
                        ids.add(parseObject.getString(COLUMN_TO_USER));
                    }
                    if (callbacks != null){
                        callbacks.onFetchedMatches(ids);
                    }
                }
            }
        });
    }

    public interface ActionDataCallbacks{
        public void onFetchedMatches(List<String> matchIds);
    }
}
