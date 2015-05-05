/*
 *  PBXList.groovy
 *
 *  Copyright © 2015 Venkat Peri.
 *
 *  This software may be modified and distributed under the terms
 *  of the MIT license.  See the LICENSE file for details.
 *
 */

package com.vperi.pbxproj

import com.vperi.pbxproj.util.PBXUuid

/**
 * Created by venkat on 5/1/15.
 */
public class PBXList extends PBXObject implements List<Object> {
    @Delegate( interfaces = false ) ArrayList<Object> list = [ ]

    PBXList() {
        this._key = PBXUuid.createId()
    }

    /**
     * Intercept list indexer to dereference PBXRef objects
     * @param idx
     * @return
     */
    public Object get( int idx ) {
        def v = list[ idx ]
        v instanceof PBXRef ? v.theObject() : v
    }

    String toString( int level = 0 ) {
        if ( level > 1 ) return "[...]"
        "PBXList" + list.collect {
            it instanceof PBXObject ? it.toString( level + 1 ) : it.toString()
        }
    }
}

