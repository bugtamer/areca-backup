#!/usr/bin/env bash
####################################################################
#
# This script launches Areca's version check module
#
####################################################################

PROGRAM_DIR=`dirname "$0"`
"${PROGRAM_DIR}"/bin/areca_run.sh com.application.areca.version.VersionCheckLauncher "$1" "$2" "$3" "$4" "$5" "$6" "$7" "$8" "$9" "${10}" "${11}" "${12}"
