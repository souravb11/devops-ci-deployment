#!/bin/bash
echo  "\n\n"
echo  "********************************************************************************"
echo  "\n"
echo  "\t\t\t BUILD SUCCESS "
echo  "\n"
bt_public_ip=`dig +short myip.opendns.com @resolver1.opendns.com`
echo  "Please use http://$bt_public_ip:8080/CounterWebApp/ to access your Web Application."
echo  "\n"
echo  " Please use http://$bt_public_ip:9000 to check the code quality"
echo  "\n"
echo  "********************************************************************************"
