/*
 *  PBXObject.groovy
 *
 *  Copyright © 2015 Venkat Peri.
 *
 *  This software may be modified and distributed under the terms
 *  of the MIT license.  See the LICENSE file for details.
 *
 */

package com.vperi.pbxproj

/**
 * Base class for PBX* objects
 */
public class PBXObject extends Expando {

    PBXObject() {}

    /**
     * Intercept getProperty() to dereference PBXRef objects
     * @param name
     * @return
     */
    @Override
    public Object getProperty( String name ) {
        def value = super.getProperty( name )
        value instanceof PBXRef ? value.theObject() : value
    }

    def walk( TreeVisitor visitor ) {
        def node = new TreeWalkNode( name: "__root", level: 0, object: this )
        walk2 node, visitor, [ ]
    }

    /**
     * Tree walker
     * @param visitor :
     * @return
     */
    private static def walk2( final TreeWalkNode node, TreeVisitor visitor, def visited ) {
        final def obj = node.object
        if ( !( obj instanceof PBXObject ) ) return
        if ( !( obj instanceof PBXRef || obj instanceof PBXLiteral ) && visited.any { it == obj._key } )
            return

        def context = visitor.visit( node )
        visited.add node.object._key

        if ( obj instanceof PBXList ) {
            obj.eachWithIndex { v, i ->
                def child = new TreeWalkNode(
                        name: i.toString(),
                        level: node.level + 1,
                        object: v,
                        parent: node,
                        parentContext: context )

                walk2 child, visitor, visited
            }
        } else {
            obj.properties.findAll { !it.key.startsWith( "_" ) }.each { k, v ->
                def child = new TreeWalkNode(
                        name: k.toString(),
                        level: node.level + 1,
                        object: v,
                        parent: node,
                        parentContext: context )
                walk2 child, visitor, visited
            }
        }
        context
    }

/**
 * Custom toString()
 * @param level
 * @return
 */
    public String toString( int level = 0 ) {
        if ( level > 1 ) return "..."
        def props = properties
        .findAll { !it.key.startsWith( "_" ) }
        .collect {
            def v = getProperty( it.key as String )
            def s = v instanceof PBXObject ? v.toString( level + 1 ) : v.toString()
            "$it.key=$s"
        }.join( ", " )

        def className = this.class ? this.class.simpleName : ( this instanceof Map ? "Map" : "donno" )
        "$className{${props}}"
    }
}























