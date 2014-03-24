package com.academy.core.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.validation.constraints.Pattern;


@Retention(RetentionPolicy.RUNTIME)
@Pattern(regexp="*@*")
public @interface Email {

}
