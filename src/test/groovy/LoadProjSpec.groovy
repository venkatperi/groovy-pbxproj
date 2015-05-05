import com.vperi.pbxproj.PBXProject
import com.vperi.pbxproj.XcodeProj
import spock.lang.Specification

/**
 * Created by venkat on 5/2/15.
 */
class LoadProjSpec extends Specification {
    def proj

    def setup() {
        def data = this.getClass().getResource( '/keymgr.pbxproj' ).text
        proj = new XcodeProj( data.bytes )
    }

    def "load a pbxproj file"() {
        expect:
        proj
    }

    def "the headers are parsed"() {
        expect:
        proj.archiveVersion == 1
        proj.objectVersion == 42
        proj.objects.size() > 0
        proj.rootObject != null
    }

    def "the root object is a PBXProject"() {
        expect:
        proj.rootObject._key == "0597689003D6465000C9149F"
        proj.rootObject instanceof PBXProject
    }

    def "object types and keys are ok"( String key, String klass ) {
        expect:
        proj.objects[ key ].class.simpleName == klass

        where:
        key                        | klass
        'F98F991811A4A86400D21E1F' | 'PBXBuildFile'
        '0597689803D6472D00C9149F' | 'PBXFileReference'
        '059768A803D6494200C9149F' | 'PBXFileReference'
        '05CA34F70433CFDF00C9149F' | 'PBXFileReference'
        'F98F991611A4A85000D21E1F' | 'PBXFileReference'
        'F98F991411A4A85000D21E1F' | 'PBXFrameworksBuildPhase'
        '0597688C03D6465000C9149F' | 'PBXGroup'
        '0597689703D646C100C9149F' | 'PBXGroup'
        'F98F991511A4A85000D21E1F' | 'PBXNativeTarget'
        '0597689003D6465000C9149F' | 'PBXProject'
        'F98F991311A4A85000D21E1F' | 'PBXSourcesBuildPhase'
        '05B1F3D8089068690080B6E2' | 'XCBuildConfiguration'
        'F98F991711A4A85100D21E1F' | 'XCBuildConfiguration'
        '05B1F3D7089068690080B6E2' | 'XCConfigurationList'
        'F98F991911A4A8B900D21E1F' | 'XCConfigurationList'
    }

    def "file references are ok"( def key, def path ) {
        expect:
        proj.objects[ key ].path == path

        where:
        key                        | path
        "0597689803D6472D00C9149F" | "keymgr.c"
        "059768A803D6494200C9149F" | "keymgr.h"
        "05CA34F70433CFDF00C9149F" | "testcases/basic-eh-app.cc"
        "F98F991611A4A85000D21E1F" | "libkeymgr.dylib"
    }

    def "verify product group -- PBXRef links"( def index, def path ) {
        given:
        def children = proj.objects[ "0597688C03D6465000C9149F" ].children

        expect:
        children[ index ].path == path

        where:
        index | path
        1     | "keymgr.c"
        2     | "keymgr.h"
        3     | "testcases/basic-eh-app.cc"
    }

    def "the source build phase has one file (also check PBXRef for .path)"() {
        given:
        def sbPhase = proj.objects[ "F98F991311A4A85000D21E1F" ]

        expect:
        sbPhase.files.size() == 1
        sbPhase.files[ 0 ].fileRef.path == "keymgr.c"
    }

    def "the project has one target"() {
        given:
        def root = proj.rootObject

        expect:
        root.targets.size() == 1
    }

    def "PBXRef dereferences ok (accessing target through root object)"() {
        expect:
        proj.rootObject.targets[ 0 ].name == "libkeymgr.dylib"
    }
}
