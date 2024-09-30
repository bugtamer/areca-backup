#!/usr/bin/env bash


# command line paramaters
uninstall_param="$1"
is_install_mode=true
if [ "$uninstall_param" == '-u' ]; then
    is_install_mode=false
fi


is_header_present() {
    header="$1"
    echo "#include <$header>" | gcc -E -x c - > /dev/null 2>&1
    if [ $? -eq 0 ]; then
        echo
        echo "'${header}' found"
        return 0
    else
        echo
        echo "'${header}' not found"
        return 1
    fi

}


manage_package() {
    header="$1"
    package="$2"
    
    is_header_installed=false
    if is_header_present "$header"; then
        is_header_installed=true
    fi
    
    # install package
    if ! $is_header_installed && $is_install_mode; then
        echo
        echo "Install $package ($header)"
        if command -v apt &> /dev/null; then
            sudo apt install -y "$package"
        elif command -v apt-get &> /dev/null; then
            sudo apt-get install -y "$package"
        fi
    fi

    # remove package
    if $is_header_installed && ! $is_install_mode; then
        echo
        echo "Uninstall $package ($header)"
        if command -v apt &> /dev/null; then
            sudo apt purge -y "$package"
        elif command -v apt-get &> /dev/null; then
            sudo apt-get purge -y "$package"
        fi
    fi
}


# pontential missing gcc headers
# See jni/com_myJava_file_metadata_posix_jni_wrapper_FileAccessWrapper.c
# See https://packages.debian.org/index
declare -A missing_headers
missing_headers['attr/xattr.h']='libattr1-dev'
missing_headers['sys/acl.h']='libacl1-dev'
# missing_headers['errno.h']='moreutils'
missing_headers['errno.h']='libzypp-dev'
# missing_headers['errno.h']='gnulib'
# missing_headers['jni.h']='default-jdk'


for header in "${!missing_headers[@]}"; do
    package="${missing_headers[$header]}"
    manage_package "$header" "$package"
done
