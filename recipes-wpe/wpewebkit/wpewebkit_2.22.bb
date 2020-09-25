require wpewebkit.inc

PV = "2.22+git${SRCPV}"
PR = "r0"

SRCREV ?= "2f7b737d735c67d8229f576d54a9c907b7057d94"
SRC_URI = "git://github.com/WebPlatformForEmbedded/WPEWebKit.git;branch=wpe-2.22 \
           file://0001-Fix-build-with-musl.patch \
           file://0002-Define-MESA_EGL_NO_X11_HEADERS-when-not-using-GLX.patch \
        "

SRC_URI += "file://0001-GSTREAMER-EME-Implement-Secure-Data-Path-in-decrypto.patch;md5sum=c11584828ed832e2152e20e342c69457" 
SRC_URI += "file://0002-GSTREAMER-Configure-dependencies-on-GStreamer-alloca.patch;md5sum=df7bcce72d952ed46ab1027e7d8d5a39" 
SRC_URI += "file://0003-GSTREAMER-EME-Support-unencrypted-content-when-SDP-i.patch;md5sum=fa42fa57951b05a21c495239615b49f7" 
SRC_URI += "file://0004-MSE-Increase-appsrc-max-bytes-to-32MB-TRIAL.patch;md5sum=8a158118c58bb601a72904f21f5e71bd" 
SRC_URI += "file://0005-EME-Support-parsing-the-ContentProtection-tag-to-ext.patch;md5sum=d9163244a77806c1126208e8335581c0" 
SRC_URI += "file://0006-GSTREAMER-EME-Report-the-support-for-HEVC-H.265.patch;md5sum=540ebe0747bf9953feb2d122cdbbdf4a" 
SRC_URI += "file://0007-GStreamer-MSE-Report-support-for-HEVC.patch;md5sum=63853c427290a0464f3c85a72c1f7ed1" 
SRC_URI += "file://0008-GStreamer-MSE-Properly-handle-the-case-where-playbac.patch;md5sum=b82654c907f593fa8e12c5ac2af3d157" 
SRC_URI += "file://0009-MMIOT-498-Set-secure-field-in-negotiated-caps-for-en.patch;md5sum=affbdf64d4dbe2483aee68137cd29a5f" 
DEPENDS += "libgcrypt"
PACKAGECONFIG_append = " webcrypto"

FILES_${PN} += "${libdir}/wpe-webkit-0.1/injected-bundle/libWPEInjectedBundle.so"
FILES_${PN}-web-inspector-plugin += "${libdir}/wpe-webkit-0.1/libWPEWebInspectorResources.so"
