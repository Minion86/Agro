/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.Adquisicion.Controller.util;

import java.util.ArrayList;

/**
 *
 * @author Nelson
 */
public class KeyObj {

    ArrayList<Object> keys;

    public KeyObj(Object... objs) {
        keys = new ArrayList<Object>();

        for (int i = 0; i < objs.length; i++) {
            keys.add(objs[i]);
        }
    }

    // Add appropriate isEqual() ... you IDE should generate this
}
