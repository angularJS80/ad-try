package com.example.jcompia.tutoralnavi3.data;

import android.accounts.AccountManager;
import android.content.SharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by janisharali on 25/12/16.
 */

@Singleton
public class AccountManagerHelper {

    private AccountManager mAccountManager;

    @Inject
    public AccountManagerHelper(AccountManager accountManager) {
        mAccountManager = accountManager;
    }

    public AccountManager getmAccountManager() {
        return mAccountManager;
    }
}
