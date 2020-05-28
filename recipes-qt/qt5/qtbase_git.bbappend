# Make the default platform be eglfs, so that apps don't need to be
# started with -platform eglfs.
QT_CONFIG_FLAGS += " -qpa eglfs "
