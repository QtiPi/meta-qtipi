# Image configured as a wired ethernet client

require recipes-core/image/core-image-qtipi.bb

IMAGE_INSTALL += "network-config-eth-client"
