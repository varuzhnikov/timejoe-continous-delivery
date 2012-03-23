sh stop.sh

grails run-app &
grails_pid=$!
echo $grails_pid > ../grails-pid.txt
