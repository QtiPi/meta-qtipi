FILESEXTRAPATHS_append := ":${THISDIR}/${PN}"

SRC_URI += "file://qmlliveruntime.service"

inherit systemd

SYSTEMD_SERVICE_${PN} = "qmlliveruntime.service"

RDEPENDS_${PN} += "backlight-setup"
do_install_append() {
    install -Dm0644 ${WORKDIR}/qmlliveruntime.service ${D}/${systemd_unitdir}/system/qmlliveruntime.service
}
