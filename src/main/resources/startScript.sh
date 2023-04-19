#!/bin/bash

download_plugins() {
    local target_dir=$1
    shift
    local plugins=("$@")

    for plugin in "${plugins[@]}"; do
        plugin_name=$(basename "$plugin")
        plugin_prefix="SIMPLECLOUD_DOWNLOAD_"
        curl -o "${target_dir}/${plugin_prefix}${plugin_name}" "$plugin"
    done
}

remove_downloaded_plugins() {
    local target_dir=$1
    local prefix="SIMPLECLOUD_DOWNLOAD_"
    find "${target_dir}" -type f -name "${prefix}*" -exec rm {} \;
}

execute_java_command() {
    local java_args=("$@")
    "${java_args[@]}"
}

setup_and_run() {
    local base_dir=$1
    local java_args=("${@:2}")
    local plugins=("${@: -1}")

    # Ensure that /{base_dir}/plugins/ exist
    mkdir -p "${base_dir}/plugins/"

    # Download SimpleCloud-Plugin.jar
    curl -o "${base_dir}/plugins/SimpleCloud-Plugin.jar" http://template-files/plugin

    # Remove previously downloaded plugins
    remove_downloaded_plugins "${base_dir}/plugins/"

    # Download additional plugins
    IFS='|' read -ra plugins_array <<< "${plugins}"
    download_plugins "${base_dir}/plugins/" "${plugins_array[@]}"

    # Set the work directory to /{base_dir}/
    cd "${base_dir}/"

    # Execute the java command passed as an input parameter
    execute_java_command "${java_args[@]}"
}

# Get the input parameter for the java command
IFS='|' read -ra java_args <<< "$1"

# Check if /static/ exists
if [ -d "/static/" ]; then
    # Copy everything from /img/ to /static/ if /static/ is empty
    if [ -z "$(find /static/ -type f)" ]; then
        echo "Copying img/ to static/"
        cp -r /img/* /static/
    fi

    setup_and_run "/static/" "${java_args[@]}" "$2"

else
    setup_and_run "/img/" "${java_args[@]}" "$2"
fi
