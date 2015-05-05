/*
 *  XcodeProj.groovy
 *
 *  Copyright © 2015 Venkat Peri.
 *
 *  This software may be modified and distributed under the terms
 *  of the MIT license.  See the LICENSE file for details.
 *
 */

package com.vperi.pbxproj

import com.dd.plist.NSArray
import com.dd.plist.NSDictionary
import com.dd.plist.NSObject
import com.dd.plist.NSString
import com.dd.plist.PropertyListParser
import com.vperi.pbxproj.util.PBXUuid

public class XcodeProj {
    private def _lookup = { k -> objects[ k ] }
    public def archiveVersion
    public def objectVersion
    public def classes
    public PBXObject rootObject
    final def objects = new PBXObjectManager()

    public XcodeProj( String path ) {
        this( PropertyListParser.parse( path ) )
    }

    public XcodeProj( File file ) {
        this( PropertyListParser.parse( file ) )
    }

    public XcodeProj( InputStream s ) {
        this( PropertyListParser.parse( s ) )
    }

    public XcodeProj( byte[] data ) {
        this( PropertyListParser.parse( data ) )
    }

    private XcodeProj( NSObject root ) {
        assert root, "root is null!"
        archiveVersion = Integer.parseInt( root.archiveVersion.toString() )
        objectVersion = Integer.parseInt( root.objectVersion.toString() )
        classes = convert( root.classes )

        root.objects.each {
            def type = it.value.isa.toString()
            def key = it.key
            def obj = objects.create( type, it.key )
            it.value.findAll { it.key != "isa" }.each {
                def val = convert( it.value )
                obj."$it.key" = val
            }
        }

        rootObject = objects[ root.rootObject.toString() ]
    }

    private def convert( NSString obj ) {
        convert( obj.toString() )
    }

    private def convert( String val ) {
        PBXUuid.isValid( val ) ? new PBXRef( val, _lookup ) : new PBXLiteral( val )
    }

    private def convert( NSArray obj ) {
        def list = new PBXList()
        for ( int i = 0; i < obj.count(); i++ ) {
            list.add( convert( obj.objectAtIndex( i ) ) )
        }
        list
    }

    private def convert( NSDictionary obj ) {
        def map = new PBXMap()
        obj.allKeys().each {
            def val = obj[ it ]
            map[ it ] = convert( val )
        }
        map
    }
}
