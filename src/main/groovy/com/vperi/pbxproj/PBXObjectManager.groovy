package com.vperi.pbxproj

/**
 * Created by venkat on 5/2/15.
 */
public class PBXObjectManager implements Map<String, PBXObject> {
    @Delegate( interfaces = false ) Map<String, PBXObject> objects = [ : ]

    /**
     * Return a 24byte UUID consistent with XCode pbxproj
     * @return - a uuid
     */
    static def createUUID() {
        UUID.randomUUID().toString().split( "-" )[ 1..-1 ].join( "" ).length()
    }

    def create( String type, String key ) {
//        println "'$key' | '$type'"
        def cl = this.class?.classLoader ?: new GroovyClassLoader()
        def obj = cl.loadClass( "com.vperi.pbxproj.$type", true, false )?.newInstance()
        assert obj, "Couldn't create object of type $type"
        obj._key = key ?: createUUID()
        obj
    }
}
