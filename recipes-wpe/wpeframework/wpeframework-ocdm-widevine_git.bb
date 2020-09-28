SUMMARY = "WPE Framework OpenCDMi module for widevine"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1fe8768cbb5fd322f7d50656133549de"

inherit distro_features_check
REQUIRED_DISTRO_FEATURES = "widevine"

require include/wpeframework-plugins.inc

DEPENDS += " widevine curl"

SRC_URI = "git://git@github.com/WebPlatformForEmbedded/OCDM-Widevine.git;protocol=https;branch=master"
SRCREV = "052b138536bf209981a9360cc9ce1ae990a792ed"

FILES_${PN} = "${datadir}/WPEFramework/OCDM/*.drm"
