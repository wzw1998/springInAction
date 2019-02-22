package com.zexing.qualifier.animal.impl;

import com.zexing.qualifier.animal.Animal;
import com.zexing.qualifier.animal.annotation.Black;
import com.zexing.qualifier.animal.annotation.Cute;
import org.springframework.stereotype.Component;

@Component
@Cute
@Black
public class Dog implements Animal {
}
