package com.vperi.pbxproj

/**
 * Created by venkat on 5/1/15.
 */
class PBXList extends PBXObject implements List<Object> {
    @Delegate( interfaces = false ) ArrayList<Object> list = [ ]

    /**
     * Intercept list indexer to dereference PBXRef objects
     * @param idx
     * @return
     */
    public Object get( int idx ) {
        def v = list[ idx ]
        v instanceof PBXRef ? v.theObject() : v
    }

    String toString( int level = 0 ) {
        if ( level > 1 ) return "[...]"
        "PBXList" + list.collect {
            it instanceof PBXObject ? it.toString( level + 1 ) : it.toString()
        }
    }
}

