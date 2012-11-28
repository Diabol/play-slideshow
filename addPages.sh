curl --request  POST --header "Content-type: application/json" http://localhost:9000/pages/add --data '{"name":"Diabol","duration":15,"url":"http://www.diabol.se"}'
curl --request POST --header "Content-type: application/json" http://localhost:9000/pages/add --data '{"name":"DN","duration":15,"url":"http://www.dn.se"}'
curl --request POST --header "Content-type: application/json" http://localhost:9000/pages/add --data '{"name":"Jenkins","duration":15,"url":"http://jenkins-ci.org/"}'

