package com.zexing.qualifier.animal.impl;

import com.zexing.qualifier.animal.Animal;
import com.zexing.qualifier.animal.annotation.Cute;
import com.zexing.qualifier.animal.annotation.Red;
import org.springframework.stereotype.Component;

@Component
@Cute
@Red
public class Cat implements Animal {
}
