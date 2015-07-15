package com.uu.rcapp.core;


import java.net.URI;

import javax.tools.SimpleJavaFileObject;

/**
 * @author    liaoyu
 * @created   Jul 15, 2015 
 */
class StringJavaFileObject extends SimpleJavaFileObject {

    final String code;

    StringJavaFileObject(String className, String code) {
        super(URI.create(className + Kind.SOURCE.extension), Kind.SOURCE);
        this.code = code;
    }

    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) {
        return code;
    }
}