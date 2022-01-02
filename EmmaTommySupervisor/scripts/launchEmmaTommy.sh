#!bin/bash
cd ..

function delete_and_create()
{
    if [ -d "$1" ]; then
        echo "$1 exists"
        cd "$1"
        rm -fr *
        cd ..
    else
        echo "$1 doesn't exists"
    fi
    mkdir -p "$1"
}

delete_and_create "log"
delete_and_create "db"
delete_and_create "data_json_missioni_download"
delete_and_create "data_json_servizi_download"
delete_and_create "data_xml_download"
delete_and_create "post_json_folder"


java -jar EmmaTommySupervisor-0.0.1-SNAPSHOT-shaded.jar


#java -jar EmmaTommySupervisor-0.0.1-SNAPSHOT-shaded.jar conf

#java -jar EmmaTommySupervisor-0.0.1-SNAPSHOT-shaded.jar /media/sapo93/DATI/GitHub/VolCal/EmmaTommy/EmmaTommySupervisor/target/conf
