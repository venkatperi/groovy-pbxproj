/*
 *  PBXUuid.groovy
 *
 *  Copyright © 2015 Venkat Peri.
 *
 *  This software may be modified and distributed under the terms
 *  of the MIT license.  See the LICENSE file for details.
 *
 */

package com.vperi.pbxproj.util

class PBXUuid {

    /**
     * Return a 24byte UUID consistent with XCode pbxproj
     * @return - a uuid
     */
    public static String createId() {
        UUID.randomUUID().toString().split( "-" )[ 1..-1 ].join( "" )
    }

    /***
     * Returns true if id is a valid PBX style id
     * @param id
     * @return
     */
    public static isValid( String id ) {
        id instanceof String && id =~ /[0-9A-F]{24}/
    }
}
