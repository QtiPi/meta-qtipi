DESCRIPTION = "QtiPi Image"

inherit core-image populate_sdk_qt5

IMAGE_FEATURES += "package-management ssh-server-openssh tools-debug"

# Add 1 GB of rootfs size
IMAGE_ROOTFS_EXTRA_SPACE = "1048576"

# System tools
IMAGE_INSTALL += " \
    systemd-analyze \
    openssh-sftp-server \
    avahi-daemon \
"

# Pi specific packages
IMAGE_INSTALL += " \
    rpi-gpio \
"

# Some useful Qt packages
IMAGE_INSTALL += "qtipi-bundle"

# Example application
IMAGE_INSTALL += "touchpoints"
