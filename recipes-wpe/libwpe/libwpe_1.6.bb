SUMMARY = "WPE WebKit backend library"
HOMEPAGE = "https://github.com/WebPlatformForEmbedded"
SECTION = "wpe"
LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = "file://COPYING;md5=371a616eb4903c6cb79e9893a5f615cc"

DEPENDS += "virtual/egl libxkbcommon"

PROVIDES += "virtual/libwpe"
RPROVIDES_${PN} += "virtual/libwpe"

SRC_URI = "git://github.com/WebPlatformForEmbedded/WPEBackend.git"
SRCREV = "baabb08d3e412c82884eedef80f71bebacc6f151"

S = "${WORKDIR}/git"

inherit cmake

CXXFLAGS += " \
-D_GLIBCXX_USE_CXX11_ABI=0 \
-D_GNU_SOURCE \
"

CFLAGS += " \
-D_GNU_SOURCE \
"

FILES_SOLIBSDEV = ""
FILES_${PN} += "${libdir}/libwpe-1.0.so ${libdir}/pkgconfig/wpe.pc"
INSANE_SKIP_${PN} ="dev-so"

RDEPENDS_${PN} = "xkeyboard-config"
RDEPENDS_${PN}_append_rpi = " wpebackend-rdk "
