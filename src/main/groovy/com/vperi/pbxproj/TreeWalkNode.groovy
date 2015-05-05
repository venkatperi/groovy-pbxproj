/*
 *  TreeWalkNode.groovy
 *
 *  Copyright © 2015 Venkat Peri.
 *
 *  This software may be modified and distributed under the terms
 *  of the MIT license.  See the LICENSE file for details.
 *
 */

package com.vperi.pbxproj

import groovy.transform.TupleConstructor

@TupleConstructor
class TreeWalkNode {
    String name
    int level
    PBXObject object
    TreeWalkNode parent
    Object parentContext
}