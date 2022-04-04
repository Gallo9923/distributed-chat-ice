#!/bin/bash

USER="swarch"
# HOSTNAMES=("xhgrid16" "xhgrid17")
HOSTNAMES=("xhgrid16", "xhgrid17")

# path="../callback-printer"
server_jar_path="./server/build/libs/server.jar"
client_jar_path="./client/build/libs/client.jar"
dest_path="/home/${USER}/nandito/test"

# $1 USERs
# $2 jar_path
# $3 HOSTNAME
# $4 dest_path
function transfer {
    local command="$2 ${1}@${3}:${4}" 
    scp -r ${command}
    echo Transfered $2 to $3
}

for t in ${HOSTNAMES[@]}; do
    # transfer "$USER" "$path" "$t" "$dest_path"
    transfer "$USER" "$server_jar_path" "$t" "$dest_path"
    transfer "$USER" "$client_jar_path" "$t" "$dest_path"
done