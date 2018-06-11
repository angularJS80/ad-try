package com.example.jcompia.tutoralnavi3.di;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by janisharali on 08/12/16.
 */

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerActivity { // PerActivity 나와바리를 만든다.
}

