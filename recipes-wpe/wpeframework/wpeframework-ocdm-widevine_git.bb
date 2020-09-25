SUMMARY = "WPE Framework OpenCDMi module for widevine"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1fe8768cbb5fd322f7d50656133549de"

inherit distro_features_check
REQUIRED_DISTRO_FEATURES = "widevine"

require include/wpeframework-plugins.inc

DEPENDS += " widevine curl"

SRC_URI = "git://git@github.com/WebPlatformForEmbedded/OCDM-Widevine.git;protocol=https;branch=master"

SRC_URI += "file://0001-MediaSystem-Update-to-new-cdm-setServiceCertificate-.patch;md5sum=662f3345dd6032a6d825a31aaba57bd0" 
SRC_URI += "file://0002-MediaSystem-fix-alignment.patch;md5sum=8799835379ea3923361600001c116480" 
SRC_URI += "file://0003-MediaSystem-Update-onKeyStatusesChange-prototype.patch;md5sum=aa1faa8090d4140db86ddfa67c6667fe" 
SRC_URI += "file://0004-MediaSystem-remove-onDirectIndividualizationRequest-.patch;md5sum=211baa2e32cee12b087c230fa5f1aa38" 
SRC_URI += "file://0005-MediaSession-remove-onDirectIndividualizationRequest.patch;md5sum=e6887b07de944e9926324dcf3a13af49" 
SRC_URI += "file://0006-MediaSession-add-widevineStatusToCString-helper.patch;md5sum=c5fd75206b2d6554fb909311afb853b5" 
SRC_URI += "file://0007-HostImplementation-use-override-keyword.patch;md5sum=c5db7031adb7e6dc78975e874d75b1fc" 
SRC_URI += "file://0008-MediaSession-add-back-onDirectIndividualizationReque.patch;md5sum=d8bc0fad5f16bebc867a5af3c1ddb2f5" 
SRC_URI += "file://0009-MediaSystem-add-date-time-as-useful-debug-in-the-log.patch;md5sum=8cfc2895c2406ab08263c45a784f2b23" 
SRC_URI += "file://0010-MediaSystem-output-CdmStatus-string-on-error-of-gene.patch;md5sum=a18b51e056faa4bc42e6c98c9e18ab3b" 
SRC_URI += "file://0011-MediaSession-retry-if-failure-due-to-nonce-flood-err.patch;md5sum=2d837bb69b39f87abd7f6cbce0a2aff7" 
SRC_URI += "file://0012-MediaSession-add-getProvisioningResponse-method.patch;md5sum=e3b24f98e13d2036facc5b23b58834ac" 
SRC_URI += "file://0013-MediaSession-add-cdm-provisioning-call-flow.patch;md5sum=e88a3f9e76427777312f4262e90dd10d" 
SRC_URI += "file://0014-OCDM-Implement-Secure-Data-Path.patch;md5sum=1e714d997e7a8e1f5e46d23f863ccf59" 
SRC_URI += "file://0015-OCDM-Configure-to-use-opaque-handle-for-SDP.patch;md5sum=5cedca37e1008887ecb8c58a061401a0" 
SRC_URI += "file://0016-Allow-to-configure-SDP.patch;md5sum=7ecd76953faabafda955604383dd55ac" 
SRC_URI += "file://0017-OCDM-Update-interface-to-IDRM.h.patch;md5sum=612ec49255e2fb1bab767d142c0f90b5" 
SRCREV = "052b138536bf209981a9360cc9ce1ae990a792ed"

FILES_${PN} = "${datadir}/WPEFramework/OCDM/*.drm"
