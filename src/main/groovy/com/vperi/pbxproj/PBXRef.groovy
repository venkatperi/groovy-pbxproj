/*
 *  PBXRef.groovy
 *
 *  Copyright © 2015 Venkat Peri.
 *
 *  This software may be modified and distributed under the terms
 *  of the MIT license.  See the LICENSE file for details.
 *
 */

package com.vperi.pbxproj

/**
 * Created by venkat on 5/1/15.
 */
public class PBXRef extends PBXObject {
    private def _ref
    private def _lookup

    /**
     * A PBXRef object takes a "key" and a means to lookup / dereference the key
     * @param ref
     * @param lookup
     */
    public PBXRef( def ref, def lookup ) {
        this.@_ref = ref
        this.@_lookup = lookup
    }

    /**
     * Returns the object we are are a reference to
     * @return -- the underlying object
     */
    def theObject() {
        this.@_lookup( this.@_ref )
    }

    /**
     * Return property from the underlying object instead
     * @param name
     * @return
     */
    public Object getProperty( String name ) {
        return theObject().getProperty( name )
    }

    /**
     * Set the named property for the underlying object
     * @param name
     * @param val
     */
    public void setProperty( String name, def val ) {
        theObject().setProperty( name, val )
    }

    public String toString( int level = 0 ) {
        theObject()
    }
}
