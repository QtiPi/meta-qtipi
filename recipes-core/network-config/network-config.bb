DESCRIPTION = "Network configuration files"
LICENSE = "MIT"
SRC_URI = " \
    file://20-eth0.network \
    file://25-wlan0-dhcp.network \
    file://25-wlan0-static.network \
    file://wpa_supplicant-wlan0.conf \
    file://hostapd-qtipi.conf \
    file://dnsmasq-qtipi \
    file://avahi-daemon-eth0.conf \
    file://avahi-daemon-wlan0.conf \
    file://avahi-eth0.conf \
    file://avahi-wlan0.conf \
"
LIC_FILES_CHKSUM = "file://20-eth0.network;md5=c8b3bdd8ab8bf2b8690f755bf581d199"
S = "${WORKDIR}"


# Set these to pre-configure the image with WPA2 Settings
# If wifi-client is used this should be the credentials of the wifi to
# connect to, and if wifi-ap is used it will be the credentials used to
# set up the AP.
QTIPI_SSID ??= "example-ssid"
QTIPI_PASSKEY ??= "example-passkey"

PACKAGES = "${PN}-wifi-ap ${PN}-wifi-client ${PN}-eth-client"

do_configure[noexec] = "1"
do_compile[noexec] = "1"
do_install() {
    install -Dm0644 ${WORKDIR}/20-eth0.network ${D}/lib/systemd/network/20-eth0.network
    install -m0644 ${WORKDIR}/25-wlan0-dhcp.network ${D}/lib/systemd/network/25-wlan0-dhcp.network
    install -m0644 ${WORKDIR}/25-wlan0-static.network ${D}/lib/systemd/network/25-wlan0-static.network
    install -Dm0644 ${WORKDIR}/wpa_supplicant-wlan0.conf ${D}/etc/wpa_supplicant/wpa_supplicant-wlan0.conf
    install -Dm0644 ${WORKDIR}/dnsmasq-qtipi ${D}/etc/dnsmasq.d/dnsmasq-qtipi
    install -Dm0644 ${WORKDIR}/hostapd-qtipi.conf ${D}/etc/hostapd-qtipi.conf

    install -Dm0644 ${WORKDIR}/avahi-daemon-wlan0.conf ${D}/etc/avahi/avahi-daemon-wlan0.conf
    install -Dm0644 ${WORKDIR}/avahi-daemon-eth0.conf ${D}/etc/avahi/avahi-daemon-eth0.conf
    install -Dm0644 ${WORKDIR}/avahi-wlan0.conf ${D}/lib/systemd/system/avahi-daemon.service.d/avahi-wlan0.conf
    install -Dm0644 ${WORKDIR}/avahi-eth0.conf ${D}/lib/systemd/system/avahi-daemon.service.d/avahi-eth0.conf

    sed 's,####SSID####,${QTIPI_SSID},' -i ${D}/etc/wpa_supplicant/wpa_supplicant-wlan0.conf
    sed 's,####PASSKEY####,${QTIPI_PASSKEY},' -i ${D}/etc/wpa_supplicant/wpa_supplicant-wlan0.conf

    sed 's,####SSID####,${QTIPI_SSID},' -i ${D}/etc/hostapd-qtipi.conf
    sed 's,####PASSKEY####,${QTIPI_PASSKEY},' -i ${D}/etc/hostapd-qtipi.conf

    install -d ${D}/${systemd_unitdir}/system/multi-user.target.wants/
    ln -sf /lib/systemd/system/wpa_supplicant@.service ${D}/${systemd_unitdir}/system/multi-user.target.wants/wpa_supplicant@wlan0.service
    ln -sf /lib/systemd/system/hostapd.service ${D}/${systemd_unitdir}/system/multi-user.target.wants/hostadp.service
}

FILES_${PN}-wifi-client = " \
    /etc/avahi/avahi-daemon-wlan0.conf \
    /lib/systemd/system/multi-user.target.wants/wpa_supplicant@wlan0.service \
    /lib/systemd/network/25-wlan0-dhcp.network \
    /etc/wpa_supplicant/* \
    /lib/systemd/system/avahi-daemon.service.d/avahi-wlan0.conf \
"

FILES_${PN}-eth-client = " \
    /etc/avahi/avahi-daemon-eth0.conf \
    /lib/systemd/network/20-eth0.network \
    /lib/systemd/system/avahi-daemon.service.d/avahi-eth0.conf \
"

FILES_${PN}-wifi-ap = " \
    /lib/systemd/network/25-wlan0-static.network \
    /etc/dnsmasq.d/dnsmasq-qtipi \
    /etc/hostapd-qtipi.conf \
    /lib/systemd/system/multi-user.target.wants/hostadp.service \
"

RDEPENDS_${PN}-wifi-ap += "hostapd dnsmasq"
