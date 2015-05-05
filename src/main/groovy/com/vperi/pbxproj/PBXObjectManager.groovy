/*
 *  PBXObjectManager.groovy
 *
 *  Copyright © 2015 Venkat Peri.
 *
 *  This software may be modified and distributed under the terms
 *  of the MIT license.  See the LICENSE file for details.
 *
 */

package com.vperi.pbxproj

import com.vperi.pbxproj.util.PBXUuid

public class PBXObjectManager implements Map<String, PBXObject> {
    @Delegate( interfaces = false ) Map<String, PBXObject> objects = [ : ]

    /**
     * Create a new object with the specified type and optional key.
     * Creates a UUID / key if needed. The new object is added to our dictionary.
     * @param type - the type of the new object (required)
     * @param key - optional key
     * @return - the created object
     */
    public def create( String type, String key, String parentKey = null ) {
        def cl = this.class?.classLoader ?: new GroovyClassLoader()
        def obj = cl.loadClass( "com.vperi.pbxproj.$type", true, false )?.newInstance()
        assert obj, "Couldn't create object of type $type"
        obj._key = key ?: PBXUuid.createId()
        obj._parentKey = parentKey
        this[ obj._key ] = obj  //store the object
        obj
    }
}
