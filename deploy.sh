if [ -e grails-pid.txt ];
	then
		kill `head -n 1 grails-pid.txt`
fi
grails run-app &
grails_pid=$!
echo $grails_pid > grails-pid.txt
