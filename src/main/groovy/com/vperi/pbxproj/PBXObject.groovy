package com.vperi.pbxproj

/**
 * Base class for PBX* objects
 */
public class PBXObject extends Expando {

    PBXObject() { }

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

























