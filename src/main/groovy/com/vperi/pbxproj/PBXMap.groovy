/*
 *  PBXMap.groovy
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
public class PBXMap extends PBXObject {

    PBXMap() {
        this._key = PBXUuid.createId()
    }
}
