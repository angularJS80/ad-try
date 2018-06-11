package com.example.jcompia.tutoralnavi3.di;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by janisharali on 25/12/16.
 */

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface ApplicationContext { // 컨텍스트를 가져다 쓸건데 ApplicationContext 어노테이션을 가져다 쓸것
}
