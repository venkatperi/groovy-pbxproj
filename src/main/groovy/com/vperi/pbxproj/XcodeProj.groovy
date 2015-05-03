package com.vperi.pbxproj

import com.dd.plist.NSArray
import com.dd.plist.NSDictionary
import com.dd.plist.NSObject
import com.dd.plist.NSString
import com.dd.plist.PropertyListParser

/**
 *  Created by venkat on 5/1/15.
 */
public class XcodeProj {
    def archiveVersion
    def objectVersion
    def classes
    def rootObject
    def objects = new PBXObjectManager()

    def _lookup = { k -> objects[ k ] }

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
        archiveVersion = Integer.parseInt( convert( root.archiveVersion ) )
        objectVersion = Integer.parseInt( convert( root.objectVersion ) )
        classes = convert( root.classes )

        root.objects.each {
            def type = it.value.isa.toString()
            def obj = objects.create( type, it.key )
            it.value.findAll { it.key != "isa" }.each {
                obj."$it.key" = convert( it.value )
            }
            objects[ it.key ] = obj
        }

        rootObject = objects[ root.rootObject.toString() ]
    }

    def convert( NSString obj ) {
        convert( obj.toString() )
    }

    def convert( String val ) {
        ( val instanceof String && val =~ /[0-9A-F]{24}/ ) ? new PBXRef( val, _lookup ) : val
    }

    def convert( NSArray obj ) {
        def list = new PBXList()
        for ( int i = 0; i < obj.count(); i++ ) {
            list.add( convert( obj.objectAtIndex( i ) ) )
        }
        list
    }

    def convert( NSDictionary obj ) {
        obj.collectEntries { [ ( it.key ): convert( it.value ) ] }
    }
}
