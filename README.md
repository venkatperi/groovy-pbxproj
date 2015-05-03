# groovy-pbxproj
A groovy lib for reading (and eventually writing) Xcode pbxproj files.

Best documentation is via tests for now.

##An Example
Load a pbxproj file.
```
def proj = new XcodeProj( "/some/project/project.pbxproj" )
assert proj.rootObject //the root object (type PBXProject)
assert proj.objects.size() > 0 // got to have some objects

def root = proj.rootObject   //use this to walk the project tree
assert root.buildConfigurationList //the build config list
```

##About the Classes
Each pbxproj ```isa``` object has a corresponding Groovy object in ```com.vperi.pbxproj```.
###Support Classes
* ```PBXObject``` (not a part of the Xcode project objects) is a common base class for all PBX* objects, and extends 
```Expando``` for dictionary backed extensible properties (thus, most PBX* classes are empty)
* ```PBXRef``` (also custom), is used for all pbxproj key-based object references. 
While the referenced object can be accessed via ```PBXRef.theObject()```, PBXRef overrides ```getProperty``` for
seamless pass-through property dereferencing:

```
///PBXRef
 public Object getProperty( String name ) {
        return theObject().getProperty( name )
    }
```
* PBXList & PBXMap are specialized ```List<>``` and ```Map<>``` implementations that intercept getters for automatically 
dereferencing PBXRefs.
* PBXObjectManager is the pbxproj's key to object dictionary. It also provides a pbxproj compatible UUID generator 
for creating objects.

