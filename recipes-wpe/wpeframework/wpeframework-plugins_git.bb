SUMMARY = "WPE Framework common plugins"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e338ae07876cc28375de01750cc8a162"
PR = "r1"

require include/wpeframework-plugins.inc

SRC_URI = "git://github.com/WebPlatformForEmbedded/WPEFrameworkPlugins.git;protocol=git;branch=master \
           file://0001-WebKitBrowser-Add-Time-to-dependencies.patch \
           file://index.html \
           file://osmc-devinput-remote.json \
           file://0001-WebKitBrowser-Default-to-1080p-instead-of-720p.patch \
           file://0001-westeros-preload-libwesteros_gl.so.0.0.0.patch \
           "

SRC_URI += "file://0001-OpenCDMi-Retrieve-sub-sample-data-from-ocdm-client.patch;md5sum=9a7d1dece9d3525c8d981cc487cdc14d" 
SRC_URI += "file://0002-OCDM-Implement-a-socket-helper-class.patch;md5sum=ff2741e022731b73b88fef7bc0c3ab6b" 
SRC_URI += "file://0003-OCDM-Implement-Secure-Data-Path.patch;md5sum=7d7fb74b344b5a7fcd44b720bd25a54b" 
SRC_URI += "file://0004-OpenCDMi-Allow-to-configure-SDP.patch;md5sum=4f0a1e5f346f77ce7da89503e583e8ce" 
SRC_URI += "file://0005-OpenCDMi-Disable-debug-log-in-socket_server_helper.patch;md5sum=f93283cb44bb441ed91688067c3c9a50" 
SRCREV = "a84313d0355b3c57033c5e24b9ac927f4bda913b"

# ----------------------------------------------------------------------------

# More complicated plugins are moved seperate includes

include include/compositor.inc
include include/ocdm.inc
include include/power.inc
include include/remotecontrol.inc
include include/snapshot.inc
include include/webkitbrowser.inc

# ----------------------------------------------------------------------------

WPEFRAMEWORK_LOCATIONSYNC_URI ?= "http://jsonip.metrological.com/?maf=true"
PLUGIN_WEBSERVER_PORT ?= "8080"
PLUGIN_WEBSERVER_PATH ?= "/var/www/"

# ----------------------------------------------------------------------------

# disabled due to upstream wpeframework & yocto compile issues
# ${@bb.utils.contains('DISTRO_FEATURES', 'bluetooth',            'bluetooth', '', d)}

PACKAGECONFIG ?= " \
    ${WPE_SNAPSHOT} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'opencdm',              'opencdmi', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'clearkey',             'opencdmi_ck', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'playready',            'opencdmi_pr', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'playready_nexus',      'opencdmi_prnx', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'playready_nexus_svp',  'opencdmi_prnx_svp', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'playready_vg',         'opencdmi_vgrdm', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'widevine',             'opencdmi_wv', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'wpeframework',         'network', '', d)} \
    ${@bb.utils.contains('MACHINE_FEATURES', 'wifi',                'network', '', d)} \
    deviceinfo dictionary locationsync monitor remote remote-devinput timesync tracing ux virtualinput webkitbrowser webserver youtube \
"

PACKAGECONFIG[bluetooth]      = "-DPLUGIN_BLUETOOTH=ON -DPLUGIN_BLUETOOTH_AUTOSTART=false,-DPLUGIN_BLUETOOTH=OFF,,dbus-glib bluez5"
PACKAGECONFIG[deviceinfo]     = "-DPLUGIN_DEVICEINFO=ON,-DPLUGIN_DEVICEINFO=OFF,"
PACKAGECONFIG[dictionary]     = "-DPLUGIN_DICTIONARY=ON,-DPLUGIN_DICTIONARY=OFF,"
PACKAGECONFIG[dsgcc_client]   = "-DPLUGIN_DSGCCCLIENT=ON,,broadcom-refsw"
PACKAGECONFIG[locationsync]   = "-DPLUGIN_LOCATIONSYNC=ON \
                                 -DPLUGIN_LOCATIONSYNC_URI=${WPEFRAMEWORK_LOCATIONSYNC_URI} \
                                ,-DPLUGIN_LOCATIONSYNC=OFF,"
PACKAGECONFIG[network]        = "-DPLUGIN_NETWORKCONTROL=ON,-DPLUGIN_NETWORKCONTROL=OFF,"
PACKAGECONFIG[monitor]        = "-DPLUGIN_MONITOR=ON \
                                 -DPLUGIN_WEBKITBROWSER_MEMORYLIMIT=1228800 \
                                 -DPLUGIN_YOUTUBE_MEMORYLIMIT=614400 \
                                 -DPLUGIN_NETFLIX_MEMORYLIMIT=307200 \
                                ,-DPLUGIN_MONITOR=OFF,"
PACKAGECONFIG[systemdconnector] = "-DPLUGIN_SYSTEMDCONNECTOR=ON,-DPLUGIN_SYSTEMDCONNECTOR=OFF,"
PACKAGECONFIG[ioconnector]    = "-DPLUGIN_IOCONNECTOR=ON,-DPLUGIN_IOCONNECTOR=OFF,"
PACKAGECONFIG[timesync]       = "-DPLUGIN_TIMESYNC=ON,-DPLUGIN_TIMESYNC=OFF,"
PACKAGECONFIG[tracing]        = "-DPLUGIN_TRACECONTROL=ON,-DPLUGIN_TRACECONTROL=OFF,"
PACKAGECONFIG[virtualinput]   = "-DPLUGIN_COMPOSITOR_VIRTUALINPUT=ON,-DPLUGIN_COMPOSITOR_VIRTUALINPUT=OFF,"
PACKAGECONFIG[webproxy]       = "-DPLUGIN_WEBPROXY=ON,-DPLUGIN_WEBPROXY=OFF,"
PACKAGECONFIG[webserver]      = "-DPLUGIN_WEBSERVER=ON \
                                 -DPLUGIN_WEBSERVER_PORT="${PLUGIN_WEBSERVER_PORT}" \
                                 -DPLUGIN_WEBSERVER_PATH="${PLUGIN_WEBSERVER_PATH}" \
                                 ,-DPLUGIN_WEBSERVER=OFF,"
PACKAGECONFIG[webshell]       = "-DPLUGIN_WEBSHELL=ON,-DPLUGIN_WEBSHELL=OFF,"
PACKAGECONFIG[wifi]           = "-DPLUGIN_WIFICONTROL=ON,-DPLUGIN_WIFICONTROL=OFF,,wpa-supplicant"
PACKAGECONFIG[wifi_rdkhal]    = "-DPLUGIN_USE_RDK_HAL_WIFI=ON,-DPLUGIN_USE_RDK_HAL_WIFI=OFF,,wifi-hal"

# ----------------------------------------------------------------------------

EXTRA_OECMAKE += " \
    -DBUILD_REFERENCE=${SRCREV} \
    -DBUILD_SHARED_LIBS=ON \
"

# ----------------------------------------------------------------------------

CXXFLAGS += "${@bb.utils.contains('DISTRO_FEATURES', 'wayland', '-DWL_EGL_PLATFORM', '', d)}"

do_install_append() {
    if ${@bb.utils.contains("PACKAGECONFIG", "webserver", "true", "false", d)}
    then
      if ${@bb.utils.contains("PACKAGECONFIG", "webkitbrowser", "true", "false", d)}
      then
          install -d ${D}/var/www
          install -m 0755 ${WORKDIR}/index.html ${D}/var/www/
      fi
      install -d ${D}${PLUGIN_WEBSERVER_PATH}
    fi
}

# ----------------------------------------------------------------------------

FILES_SOLIBSDEV = ""

FILES_${PN} += "${libdir}/wpeframework/plugins/*.so ${libdir}/*.so ${datadir}/WPEFramework/* /var/www/index.html"

INSANE_SKIP_${PN} += "libdir staticdev dev-so"
INSANE_SKIP_${PN}-dbg += "libdir"
