/*
 *  PBXLiteral.groovy
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
 * Created by venkat on 5/3/15.
 */
class PBXLiteral<T> extends PBXObject {
    T _value

    public PBXLiteral( T value ) {
        this.@_value = value
        this._key = PBXUuid.createId()
    }

    Object value() {
        return this.@_value
    }

    Object getProperty( String name ) {
        switch ( name ) {
            case "_value": return this.@_value
            default: return super.getProperty( name )
        }
    }

    boolean equals( Object obj ) {
        return this.@_value.equals( obj )
    }

    public String toString() {
        return this.@_value.toString()
    }
}
