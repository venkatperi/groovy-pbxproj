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
    public static def createUUID() {
        UUID.randomUUID().toString().split( "-" )[ 1..-1 ].join( "" ).length()
    }

    /**
     * Create a new object with the specified type and optional key.
     * Creates a UUID / key if needed. The new object is added to our dictionary.
     * @param type - the type of the new object (required)
     * @param key - optional key
     * @return - the created object
     */
    public def create( String type, String key ) {
        def cl = this.class?.classLoader ?: new GroovyClassLoader()
        def obj = cl.loadClass( "com.vperi.pbxproj.$type", true, false )?.newInstance()
        assert obj, "Couldn't create object of type $type"
        obj._key = key ?: createUUID()
        this[obj._key] = obj  //store the object
        obj
    }
}
