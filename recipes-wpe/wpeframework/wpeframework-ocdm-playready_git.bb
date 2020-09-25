SUMMARY = "WPE Framework OpenCDMi module for playready"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1fe8768cbb5fd322f7d50656133549de"

inherit distro_features_check
REQUIRED_DISTRO_FEATURES = "playready"

require include/wpeframework-plugins.inc

DEPENDS += " playready"

SRC_URI = "git://git@github.com/WebPlatformForEmbedded/OCDM-Playready.git;protocol=https;branch=master"

SRC_URI += "file://0001-wpeframework-ocdm-playready-linaro-playready-support.patch;md5sum=5163a469df9c8bd5ae55b0189c4044f2" 
SRC_URI += "file://0002-Implement-Secure-Data-Path.patch;md5sum=8c0eb03b06ae593712a54a54fff2ecba" 
SRCREV = "023a064e3e87de8a9934394fd835f57f37bbcb10"

FILES_${PN} = "${datadir}/WPEFramework/OCDM/*.drm"

RDEPENDS_${PN} += " playready-data"
