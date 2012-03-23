if [ -e ../grails-pid.txt ];
	then
		kill `head -n 1 ../grails-pid.txt`
fi