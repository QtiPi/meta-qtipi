SUMMARY = "A set of useful packages when starting a Qt project"

inherit packagegroup

RDEPENDS_${PN} = " \
    qtbase \
    qtbase-plugins \
    qtdeclarative \
    qtdeclarative-tools \
    qtmultimedia \
    qtwebengine \
    qtsvg \
    qtserialport \
    qtquickcontrols \
    qtmqtt \
    qtimageformats \
    qtxmlpatterns \
    qtwebsockets \
    qtsensors \
    qtlocation \
    qtquickcontrols2 \
    qt3d \
    liberation-fonts \
    qmllive \
"
