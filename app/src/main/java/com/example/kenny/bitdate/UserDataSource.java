package com.example.kenny.bitdate;

import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kenny on 2015-07-26.
 */
public class UserDataSource {

    private static User sCurrentUser;

    private static final String TAG = "UserDataSource";
    private static final String COLUMN_FIRST_NAME = "firstName";
    private static final String COLUMN_PICTURE_URL = "pictureURL";
    private static final String COLUMN_FACEBOOK_ID = "facebookId";
    private static final String COLUMN_ID = "objectId";


    public static User getCurrentUser(){
        if (sCurrentUser == null && ParseUser.getCurrentUser() != null){
            sCurrentUser = parseUserToUser(ParseUser.getCurrentUser());
        }
        return sCurrentUser;
    }

    public static void getUnseenUsers(final UserDataCallbacks callback){
        ParseQuery<ParseObject> seenUsersQuery = new ParseQuery<ParseObject>(ActionDataSource.TABLE_NAME);
        seenUsersQuery.whereEqualTo(ActionDataSource.COLUMN_BY_USER, ParseUser.getCurrentUser().getObjectId());
        seenUsersQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null){
                    List<String> ids = new ArrayList<String>();
                    for (ParseObject parseObject: list){
                        ids.add(parseObject.getString(ActionDataSource.COLUMN_TO_USER));
                    }

                    ParseQuery<ParseUser> query = ParseUser.getQuery();
                    query.whereNotEqualTo("objectId", getCurrentUser().getID());
                    query.whereNotContainedIn("objectId",  ids);
                    query.findInBackground(new FindCallback<ParseUser>() {
                        @Override
                        public void done(List<ParseUser> list, ParseException e) {
                            formatCallback(list, e, callback);
                        }
                    });
                }
            }
        });
    }

    private static void formatCallback(List<ParseUser> list, ParseException e, UserDataCallbacks callback) {
        if (e == null){
            List<User> users = new ArrayList<User>();
            for (ParseUser parseUser: list){
                User user = parseUserToUser(parseUser);
                users.add(user);
            }
            if (callback != null) {
                callback.onUsersFetched(users);
            }
        }
    }

    public static void getUsersIn(final List<String> ids, final UserDataCallbacks callbacks){
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereContainedIn(COLUMN_ID, ids);
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> list, ParseException e) {
                formatCallback(list, e, callbacks);
            }
        });
    }

    private static User parseUserToUser(ParseUser parseUser ){
        User user = new User();
        user.setFirstName(parseUser.getString(COLUMN_FIRST_NAME));
        user.setPictureURL(parseUser.getString(COLUMN_PICTURE_URL));
        user.setFacebookID(parseUser.getString(COLUMN_FACEBOOK_ID));
        user.setID(parseUser.getObjectId());
        return user;
    }

    public interface UserDataCallbacks {
        public void onUsersFetched(List<User> users);
    }
}
