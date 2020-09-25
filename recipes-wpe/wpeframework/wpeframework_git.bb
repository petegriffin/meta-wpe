SUMMARY = "Web Platform for Embedded Framework"
HOMEPAGE = "https://github.com/WebPlatformForEmbedded"
SECTION = "wpe"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1fe8768cbb5fd322f7d50656133549de"
PR = "r0"

require include/wpeframework.inc
require include/compositor.inc


DEPENDS = "zlib python-jsonref-native virtual/egl ${WPE_COMPOSITOR_DEP} gstreamer1.0-plugins-base"
DEPENDS_append_libc-musl = " libexecinfo"
DEPENDS += "${@bb.utils.contains('PACKAGECONFIG', 'testapp', 'gtest', '', d)}"

PV = "3.0+git${SRCPV}"

SRC_URI = "git://github.com/WebPlatformForEmbedded/WPEFramework.git \
           file://wpeframework-init \
           file://wpeframework.service.in \
           file://0001-Thread.cpp-Include-limits.h-for-PTHREAD_STACK_MIN-de.patch \
           "

SRC_URI += "file://0001-OCDM-Map-source-data-as-read-only.patch;md5sum=0020477af82e67fe4392336177d86a1c" 
SRC_URI += "file://0002-OCDM-Communicate-sub-sample-data-to-OCDMi-plugin.patch;md5sum=9b5879063a27f1c89624183fe95cd285" 
SRC_URI += "file://0003-OCDM-Add-socket-helper-class.patch;md5sum=e2bfcaa9dc074e62463f73a9fb7a9640" 
SRC_URI += "file://0004-OCDM-Dependency-on-GStreamer-allocators.patch;md5sum=de994736d53ea5b699921d3571d2ccc5" 
SRC_URI += "file://0005-OCDM-Implement-Secure-Data-Path-support.patch;md5sum=6901bc2d4bb716b4e03db8e6bac0839e" 
SRC_URI += "file://0006-compositorClient-Wayland-Add-Pointer-hooks.patch;md5sum=fa4499a9995f9577864f811750f8b6d5" 
SRC_URI += "file://0007-compositorClient-Weston.cpp-Glue-wl-pointer-events-t.patch;md5sum=0c8ac0d026cd8b79f5ef624233d4f327" 
SRC_URI += "file://0008-compositorClient-Westeros-glue-wl-pointer-events-to-.patch;md5sum=1ff10d4ca0ad351fa46e1d79e1ad1e24" 
SRC_URI += "file://0009-ocdm-Allow-to-configure-SDP.patch;md5sum=f71561dbbe7747b44291b081e19b8f80" 
SRC_URI += "file://0010-Wayland-Weston-update-to-xdg_shell-stable-instead-of.patch;md5sum=c4a372c62a0ac72c6bedc0673f533fab" 
SRC_URI += "file://0011-FindWestonClient.cmake-fix-a-few-errors.patch;md5sum=bc82f6ad33088021dbe3ec9bc882a191" 
SRC_URI += "file://0012-FindWestonClient.cmake-Update-to-use-weston-desktop-.patch;md5sum=f35d03968177aa739bd666312533d074" 
SRC_URI += "file://0013-remove-xdg-shell-unstable-v6-header.patch;md5sum=65957197c7b6a7b9d33437730b173c5f" 
SRC_URI += "file://0014-Wayland-Add-xdg-shell-protocol-files.patch;md5sum=ee8d84a0ea2dbbedabbacd19d27acf5e" 
SRC_URI += "file://0015-Wayland-CMakeLists.txt-Include-xdg-shell-protocol.c-.patch;md5sum=dd6647eb5073eed26ff638cdde39a185" 
SRC_URI += "file://0016-FindWestonClient.cmake-Update-to-detect-that-Weston-.patch;md5sum=9ee1a44a91a9328b7b8377b6948f88e8" 
SRC_URI += "file://0017-CMakeLists.txt-only-add-WesterosClient-library-depen.patch;md5sum=07d9c58216fbd6c589420a1f506940bd" 
SRC_URI += "file://0018-MMIOT-570-remove-wl_pointer_listener.pointerMotion-l.patch;md5sum=ed9badbbf02d977fbda72697163182cc" 
SRCREV = "76d4cab6ae0e9161ac2530f2564002ab4a8e0fb6"

inherit cmake pkgconfig systemd update-rc.d

# Yocto root is under /home/root
WPEFRAMEWORK_PERSISTENT_PATH = "/home/root"
WPEFRAMEWORK_SYSTEM_PREFIX = "OE"
WPEFRAMEWORK_AUTOSTART ?= "false"

PACKAGECONFIG ?= " \
    release \
    ${@bb.utils.contains('DISTRO_FEATURES', 'bluetooth', 'bluetooth', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'opencdm', 'opencdm', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'provisionproxy', 'provisionproxy', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'opencdm', 'opencdm opencdm_gst', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'playready_nexus_svp', 'opencdmi_prnx_svp', '', d)} \
    compositorclient virtualinput websource webkitbrowser \
    "

# Buildtype
# Maybe we need to couple this to a Yocto feature
PACKAGECONFIG[debug]          = "-DBUILD_TYPE=Debug,,"
PACKAGECONFIG[debugoptimized] = "-DBUILD_TYPE=DebugOptimized,,"
PACKAGECONFIG[releasesymbols] = "-DBUILD_TYPE=ReleaseSymbols,,"
PACKAGECONFIG[release]        = "-DBUILD_TYPE=Release,,"
PACKAGECONFIG[production]     = "-DBUILD_TYPE=Production,,"

PACKAGECONFIG[compositorclient] = "-DCOMPOSITORCLIENT=ON,-DCOMPOSITORCLIENT=OFF"
PACKAGECONFIG[cyclicinspector]  = "-DTEST_CYCLICINSPECTOR=ON,-DTEST_CYCLICINSPECTOR=OFF,"
PACKAGECONFIG[provisionproxy]   = "-DPROVISIONPROXY=ON,-DPROVISIONPROXY=OFF,libprovision"
PACKAGECONFIG[testloader]       = "-DTEST_LOADER=ON,-DTEST_LOADER=OFF,"
PACKAGECONFIG[virtualinput]     = "-DVIRTUALINPUT=ON,-DVIRTUALINPUT=OFF,"
PACKAGECONFIG[bluetooth]        = "-DBLUETOOTH_SUPPORT=ON,-DBLUETOOTH_SUPPORT=OFF,bluez5"
PACKAGECONFIG[testapp]          = "-DBUILD_TESTS=ON,-DBUILD_TESTS=OFF"

# OCDM
PACKAGECONFIG[opencdm]          = "-DCDMI=ON,-DCDMI=OFF,"
PACKAGECONFIG[opencdm_gst]      = '-DCDMI_ADAPTER_IMPLEMENTATION="gstreamer",-DCDMI=OFF,gstreamer1.0'
PACKAGECONFIG[opencdmi_prnx_svp]= '-DCDMI_BCM_NEXUS_SVP=ON -DCDMI_ADAPTER_IMPLEMENTATION="broadcom-svp",,'

# FIXME
# The WPEFramework also needs limited Plugin info in order to determine what to put in the "resumes" configuration
# it feels a bit the other way around but lets set at least webserver and webkit
PACKAGECONFIG[websource]       = "-DPLUGIN_WEBSERVER=ON,,"
PACKAGECONFIG[webkitbrowser]   = "-DPLUGIN_WEBKITBROWSER=ON,,"

# FIXME, determine this a little smarter
# Provision event is required for libprovision and provision plugin
# Network is provided by the Network control plugin
# Location event is required for locationsync plugin
# Time event is required for timesync plugin
# Identifier event is required for Compositor plugin
# Internet event is provided by the LocationSync plugin
# WebSource event is provided by the WebServer plugin

# Only enable certain events if wpeframework is in distro features
WPEFRAMEWORK_DIST_EVENTS ?= "${@bb.utils.contains('DISTRO_FEATURES', 'wpeframework', 'Network', '', d)}"

WPEFRAMEWORK_EXTERN_EVENTS ?= " \
    ${@bb.utils.contains('PACKAGECONFIG', 'opencdm', 'Decryption', '', d)} \
    ${@bb.utils.contains('PACKAGECONFIG', 'provisionproxy', 'Provisioning', '', d)} \
    ${@bb.utils.contains('PACKAGECONFIG', 'websource', 'WebSource', '', d)} \
    ${WPEFRAMEWORK_DIST_EVENTS} \
    Location Time Internet \
"

EXTRA_OECMAKE += " \
    -DINSTALL_HEADERS_TO_TARGET=ON \
    -DEXTERN_EVENTS="${WPEFRAMEWORK_EXTERN_EVENTS}" \
    -DBUILD_SHARED_LIBS=ON \
    -DRPC=ON \
    -DBUILD_REFERENCE=${SRCREV} \
    -DTREE_REFERENCE=${SRCREV} \
    -DPERSISTENT_PATH=${WPEFRAMEWORK_PERSISTENT_PATH} \
    -DSYSTEM_PREFIX=${WPEFRAMEWORK_SYSTEM_PREFIX} \
    -DPLUGIN_COMPOSITOR_IMPLEMENTATION=${WPE_COMPOSITOR_IMPL} \
    -DPLUGIN_COMPOSITOR_SUB_IMPLEMENTATION=${WPE_COMPOSITOR_SUB_IMPL} \
    -DPYTHON_EXECUTABLE=${STAGING_BINDIR_NATIVE}/python-native/python \
"

CXXFLAGS += "${@bb.utils.contains('DISTRO_FEATURES', 'wayland', '-DWL_EGL_PLATFORM', '', d)}"

do_install_append() {
    if ${WPEFRAMEWORK_AUTOSTART}
    then

    if ${@bb.utils.contains("DISTRO_FEATURES", "systemd", "true", "false", d)}
    then
        if ${@bb.utils.contains("MACHINE_FEATURES", "platformserver", "true", "false", d)}
        then
           extra_after=""
        elif ${@bb.utils.contains("PREFERRED_PROVIDER_virtual/egl", "broadcom-refsw", "true", "false", d)}
        then
           extra_after="nxserver.service"
        fi
        extra_after="${extra_after} ${WAYLAND_COMPOSITOR}"
        install -d ${D}${systemd_unitdir}/system
        sed -e "s|@EXTRA_AFTER@|${extra_after}|g" < ${WORKDIR}/wpeframework.service.in > ${D}${systemd_unitdir}/system/wpeframework.service
    else
        install -d ${D}${sysconfdir}/init.d
        install -m 0755 ${WORKDIR}/wpeframework-init ${D}${sysconfdir}/init.d/wpeframework
    fi

    fi

    if ${@bb.utils.contains("PACKAGECONFIG", "opencdm", "true", "false", d)}
    then
        #install -d ${STAGING_INCDIR}
        install -m 0644 ${D}${includedir}/WPEFramework/interfaces/IDRM.h ${D}${includedir}/cdmi.h
    fi
}

SYSTEMD_SERVICE_${PN} = "${@bb.utils.contains('WPEFRAMEWORK_AUTOSTART', 'true', 'wpeframework.service', '', d)}"

# ----------------------------------------------------------------------------

PACKAGES =+ "${PN}-initscript"

FILES_${PN}-initscript = "${sysconfdir}/init.d/wpeframework"

FILES_SOLIBSDEV = ""
FILES_${PN} += "${libdir}/*.so ${datadir}/WPEFramework/* ${PKG_CONFIG_DIR}/*.pc"
FILES_${PN} += "${includedir}/cdmi.h"

# ----------------------------------------------------------------------------

INITSCRIPT_PACKAGES = "${PN}-initscript"
INITSCRIPT_NAME_${PN}-initscript = "wpeframework"

# If WPE Framework is enabled as distro feature, start earlier. Assuming packagegroup-wpe-boot is used and we're in control for the network
WPEFRAMEWORK_START = "${@bb.utils.contains('DISTRO_FEATURES', 'wpeframework', '40', '80', d)}"

INITSCRIPT_PARAMS_${PN}-initscript = "defaults ${WPEFRAMEWORK_START} 24"

RRECOMMENDS_${PN} = "${PN}-initscript"

# ----------------------------------------------------------------------------

INSANE_SKIP_${PN} += "dev-so"
INSANE_SKIP_${PN}-dbg += "dev-so"

# ----------------------------------------------------------------------------

RDEPENDS_${PN}_rpi = "userland"
