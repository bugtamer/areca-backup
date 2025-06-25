#!/usr/bin/env bash
####################################################################
#
# This script launches Areca's Text user interface (Debug Mode).
#
####################################################################

PROGRAM_DIR=`dirname "$0"`
"${PROGRAM_DIR}"/debug_areca_run.sh com.application.areca.launcher.tui.Launcher "$1" "$2" "$3" "$4" "$5" "$6" "$7" "$8" "$9" "${10}" "${11}" "${12}"
